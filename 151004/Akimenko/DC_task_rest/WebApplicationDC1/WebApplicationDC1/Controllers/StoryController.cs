using Microsoft.AspNetCore.Mvc;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Services.Interfaces;


namespace WebApplicationDC1.Controllers
{
    [Route("api/v1.0/storys")]
    [ApiController]
    public class StoryController(IStoryService StoryService, ILogger<StoryController> Logger)
        : AbstractController<Story, StoryRequestTO, StoryResponseTO>(StoryService, Logger)
    { }
}
