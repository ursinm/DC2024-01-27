using lab2.DTO;
using lab2.DTO.Interface;
using lab2.Services.Interface;
using Microsoft.AspNetCore.Mvc;

namespace lab2.Controllers
{
    [ApiController]
    [Route("/api/v1.0/news")]
    public class NewsController(INewsService NewsService) : Controller
    {
        [HttpGet]
        public JsonResult GetNews()
        {
            try
            {
                var News = NewsService.GetAllEntity();
                return Json(News);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }
        }

        [HttpGet]
        [Route("{NewsId:int}")]
        public async Task<JsonResult> GetNewsById(int NewsId)
        {
            try
            {
                var News = await NewsService.GetEntityById(NewsId);
                return Json(News);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }
        }

        [HttpPost]
        public async Task<JsonResult> CreateNews(NewsRequestTo NewsTo)
        {
            try
            {
                Response.StatusCode = 201;
                var News = await NewsService.CreateEntity(NewsTo);
                return Json(News);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateNews(NewsRequestTo NewsTo)
        {
            IResponseTo newNews;
            try
            {
                newNews = await NewsService.UpdateEntity(NewsTo);
                Response.StatusCode = 200;
                return Json(newNews);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{NewsId}")]
        public async Task<IActionResult> DeleteNews(int NewsId)
        {
            try
            {
                Response.StatusCode = 204;
                await NewsService.DeleteEntity(NewsId);
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
