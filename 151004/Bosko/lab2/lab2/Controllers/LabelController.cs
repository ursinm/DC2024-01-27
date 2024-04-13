using lab2.DTO;
using lab2.DTO.Interface;
using lab2.Models;
using lab2.Services.Interface;
using Microsoft.AspNetCore.Mvc;

namespace lab2.Controllers
{
    [ApiController]
    [Route("/api/v1.0/labels")]
    public class LabelController(ILabelService LabelService) : Controller
    {
        [HttpGet]
        public JsonResult GetLabel()
        {
            try
            {
                var Labels = LabelService.GetAllEntity();
                return Json(Labels);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }
        }

        [HttpGet]
        [Route("{LabelId:int}")]
        public async Task<JsonResult> GetLabelById(int LabelId)
        {
            try
            {
                var Label = await LabelService.GetEntityById(LabelId);
                return Json(Label);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }
        }

        [HttpPost]
        public async Task<JsonResult> CreateLabel(LabelRequestTo LabelTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Label = await LabelService.CreateEntity(LabelTo);
                return Json(Label);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateLabel(LabelRequestTo LabelTo)
        {
            IResponseTo newLabel;
            try
            {
                newLabel = await LabelService.UpdateEntity(LabelTo);
                Response.StatusCode = 200;
                return Json(newLabel);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{LabelId}")]
        public async Task<IActionResult> DeleteLabel(int LabelId)
        {
            try
            {
                Response.StatusCode = 204;
                await LabelService.DeleteEntity(LabelId);
            }
            catch
            {
                Response.StatusCode = 401;
                return BadRequest();
            }

            return NoContent();
        }
    }
}
