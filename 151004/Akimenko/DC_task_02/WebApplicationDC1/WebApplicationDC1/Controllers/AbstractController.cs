using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using System.Net;
using WebApplicationDC1.Services;

namespace WebApplicationDC1.Controllers
{
    public abstract class AbstractController<Entity, RequestTO, ResponseTO>
        (ICRUDService<Entity, RequestTO, ResponseTO> Service, ILogger Logger) : Controller
        where Entity : class
        where RequestTO : class
        where ResponseTO : class
    {
        [HttpGet]
        public virtual JsonResult Read()
        {
            var Creators = Service.GetAll();

            Logger.LogInformation("Getting all {type}", typeof(Entity));

            return Json(Creators);
        }

        [HttpPost]
        public virtual async Task<JsonResult> Create([FromBody] RequestTO Creator)
        {
            ResponseTO? response = null;
            Logger.LogInformation("Creating {res}", Json(Creator).Value);
            Response.StatusCode = (int)HttpStatusCode.Created;

            try
            {
                response = await Service.Add(Creator);
                return Json(response);
            }
            catch (Exception ex)
            {
                Logger.LogError("Invalid request at ADD {type} {ex}", typeof(Entity), ex);

                if (ex.Message.Contains("Creator with this login already exists"))
                {
                    Response.StatusCode = (int)HttpStatusCode.Forbidden;
                    return Json(new { error = ex.Message });
                }

                Response.StatusCode = (int)HttpStatusCode.BadRequest;
                return Json(new { error = ex.Message });

                //Response.StatusCode = (int)HttpStatusCode.BadRequest;
                //return Json(new { error = ex.Message });
            }

            //return Json(response);
        }

        [HttpPut]
        public async Task<JsonResult> Update([FromBody] RequestTO request)
        {
            ResponseTO? response = null;
            Logger.LogInformation("Updating {entity}: {request}", typeof(Entity), Json(request).Value);

            try
            {
                response = await Service.Update(request);
            }
            catch (Exception ex)
            {
                Logger.LogError("Invalid request at UPDATE {type} {ex}", typeof(Entity), ex);
                Response.StatusCode = (int)HttpStatusCode.BadRequest;
            }

            if (response is null)
            {
                return Json(new EmptyResult());
            }

            return Json(response);
        }

        [HttpPatch]
        [Route("{id:int}")]
        public async Task<JsonResult> PartialUpdate([FromRoute] int id, [FromBody] JsonPatchDocument<Entity> patch)
        {
            ResponseTO? response = null;
            Logger.LogInformation("Patching {Creator}", patch);

            try
            {
                response = await Service.Patch(id, patch);
            }
            catch (Exception ex)
            {
                Logger.LogError("Invalid request at PATCH {type} {ex}", typeof(Entity), ex);
                Response.StatusCode = (int)HttpStatusCode.BadRequest;
            }

            return Json(response);
        }

        [HttpDelete]
        [Route("{id:int}")]
        public async Task<IActionResult> Delete([FromRoute] int id)
        {
            bool res = false;
            Logger.LogInformation("Deleted {type} {id}", typeof(Entity), id);

            try
            {
                res = await Service.Remove(id);
                if (res)
                {
                    return NoContent(); // 204 No Content
                }
                else
                {
                    Logger.LogInformation("Deleting failed {id}", id);
                    return BadRequest();
                }
            }
            catch
            {
                Logger.LogInformation("Deleting failed {id}", id);
                return BadRequest();
            }

            //return res ? NoContent() : BadRequest();
        }

        [HttpGet]
        [Route("{id:int}")]
        public async Task<JsonResult> GetByID([FromRoute] int id)
        {
            Logger.LogInformation("Get {type} {id}", typeof(Entity), id);
            ResponseTO? response = null;

            try
            {
                response = await Service.GetByID(id);
            }
            catch
            {
                Logger.LogError("ERROR getting {type} {id}", typeof(Entity), id);
                Response.StatusCode = (int)HttpStatusCode.NotFound;
            }

            return Json(response);
        }
    }
}
