using Microsoft.AspNetCore.Mvc;
using System.Net;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Services.Interfaces;

namespace WebApplicationDC1.Controllers
{
    [Route("api/v1.0/creators")]
    [ApiController]
    public class CreatorController(ICreatorService CreatorService, ILogger<CreatorController> Logger) :
        AbstractController<Creator, CreatorRequestTO, CreatorResponseTO>(CreatorService, Logger)
    {
        [HttpGet]
        [Route("storys/{id:int}")]
        public async Task<JsonResult> GetByStoryID([FromRoute] int id)
        {
            CreatorResponseTO? response = null;
            Logger.LogInformation("Getting Creator by story ID: {id}", id);

            try
            {
                response = await CreatorService.GetByStoryID(id);
            }
            catch (Exception ex)
            {
                Logger.LogError("Error getting Creator by story ID {ex}", ex);
                Response.StatusCode = (int)HttpStatusCode.BadRequest;
            }

            return Json(response);
        }
    }
}
