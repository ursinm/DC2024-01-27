using Microsoft.AspNetCore.Mvc;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Services.Interfaces;

namespace WebApplicationDC1.Controllers
{
    [Route("api/v1.0/posts")]
    [ApiController]
    public class PostController(IPostService PostService, ILogger<PostController> Logger)
        : AbstractController<Post, PostRequestTO, PostResponseTO>(PostService, Logger)
    { }
}
