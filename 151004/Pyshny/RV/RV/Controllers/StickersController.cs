using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Npgsql;
using RV.Models;
using RV.Services.DataProviderServices;
using RV.Views.DTO;

namespace RV.Controllers
{
    [Route("api/v1.0/stickers")]
    [ApiController]
    public class StickersController : ControllerBase
    {
        private readonly IDataProvider _context;

        public StickersController(IDataProvider context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetStickers()
        {
            return StatusCode(200, _context.GetStickers());
        }

        [HttpGet("{id}")]
        public IActionResult GetSticker(int id)
        {
            return StatusCode(200, _context.GetSticker(id));
        }

        [HttpPut]
        public IActionResult UpdateSticker([FromBody] StickerUpdateDTO sticker)
        {
            try
            {
                return StatusCode(200, _context.UpdateSticker(sticker));
            }
            catch (DbUpdateException ex)
            {
                string errorCode = "400";
                string hash = ex.Message.GetHashCode().ToString();
                errorCode += hash[0];
                errorCode += hash[1];
                Dictionary<string, string> error = new Dictionary<string, string>() {
                    { "errorMeassage", ex.Message},
                    { "errorCode", errorCode}
                };
                return StatusCode(400, error);
            }
        }

        [HttpPost]
        public IActionResult PostSticker([FromBody] StickerAddDTO sticker)
        {
            try { 
                return StatusCode(201, _context.CreateSticker(sticker));
            }
            catch (DbUpdateException ex)
            {
                int statusCode = 400;
                if (ex.InnerException is PostgresException postgresException)
                {
                    if (postgresException.SqlState == "23505")
                    {
                        statusCode = 403;
                    }
                }
                string hash = ex.Message.GetHashCode().ToString();
                string errorCode = statusCode.ToString();
                errorCode += hash[0];
                errorCode += hash[1];
                Dictionary<string, string> error = new Dictionary<string, string>() {
                    { "errorMeassage", ex.Message},
                    { "errorCode", errorCode}
                };
                return StatusCode(statusCode, error);
            }
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteSticker(int id)
        {
            int res = _context.DeleteSticker(id);
            if (res == 0)
                return StatusCode(400);
            else
                return StatusCode(204);
        }
    }
}
