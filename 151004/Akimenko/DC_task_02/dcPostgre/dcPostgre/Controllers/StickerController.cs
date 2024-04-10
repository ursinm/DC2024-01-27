using Microsoft.AspNetCore.Mvc;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Services.Interfaces;

namespace WebApplicationDC1.Controllers
{
    [Route("api/v1.0/stickers")]
    [ApiController]
    public class StickerController(IStickerService StickerService, ILogger<StickerController> Logger) :
        AbstractController<Sticker, StickerRequestTO, StickerResponseTO>(StickerService, Logger)
    { }
}
