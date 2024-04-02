using Lab1.DTO.Interface;
using Lab1.DTO;
using Lab1.Services;
using Lab1.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace Lab1.Controllers
{
    [ApiController]
    [Route("/api/v1.0/markers")]
    public class MarkerController(IMarkerService MarkerService) : Controller
    {
        [HttpGet]
        public JsonResult GetMarkers()
        {
            try
            {
                var Markers = MarkerService.GetAllEnt();
                return Json(Markers);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());

            }

        }

        [HttpGet]
        [Route("{MarkerId:int}")]
        public async Task<JsonResult> GetMarkerById(int MarkerId)
        {
            try
            {
                var Marker = await MarkerService.GetEntById(MarkerId);
                return Json(Marker);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }


        }

        [HttpPost]
        public async Task<JsonResult> CreateMarker(MarkerRequestTo MarkerTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Marker = await MarkerService.CreateEnt(MarkerTo);
                return Json(Marker);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateMarker(MarkerRequestTo MarkerTo)
        {
            IResponseTo newMarker;
            try
            {
                newMarker = await MarkerService.UpdateEnt(MarkerTo);
                Response.StatusCode = 200;
                return Json(newMarker);

            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{MarkerId}")]
        public async Task<IActionResult> DeleteMarker(int MarkerId)
        {
            try
            {
                Response.StatusCode = 204;
                await MarkerService.DeleteEnt(MarkerId);
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
