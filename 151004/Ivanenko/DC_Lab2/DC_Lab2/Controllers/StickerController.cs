using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using DC_Lab1.Services;
using DC_Lab1.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace DC_Lab1.Controllers
{
    [ApiController]
    [Route("/api/v1.0/stickers")]
    public class StickerController(IStickerService StickerService) : Controller
    {
        [HttpGet]
        public JsonResult GetStickers()
        {
            try
            {
                var Stickers = StickerService.GetAllEnt();
                return Json(Stickers);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());

            }

        }

        [HttpGet]
        [Route("{StickerId:int}")]
        public async Task<JsonResult> GetStickerById(int StickerId)
        {
            try
            {
                var Sticker = await StickerService.GetEntById(StickerId);
                return Json(Sticker);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }


        }

        [HttpPost]
        public async Task<JsonResult> CreateSticker(StickerRequestTo StickerTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Sticker = await StickerService.CreateEnt(StickerTo);
                return Json(Sticker);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateSticker(StickerRequestTo StickerTo)
        {
            IResponseTo newSticker;
            try
            {
                newSticker = await StickerService.UpdateEnt(StickerTo);
                Response.StatusCode = 200;
                return Json(newSticker);

            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{StickerId}")]
        public async Task<IActionResult> DeleteSticker(int StickerId)
        {
            try
            {
                Response.StatusCode = 204;
                await StickerService.DeleteEnt(StickerId);
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
