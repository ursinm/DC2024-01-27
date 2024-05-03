using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Publisher.Controllers.V1_0.Common;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Service.Interface;

namespace Publisher.Controllers.V1_0
{
    [Route("api/v1.0/storys")]
    [ApiController]
    public class storyController(IstoryService storyService, ILogger<storyController> Logger, IMapper Mapper)
        : AbstractController<story, storyRequestTO, storyResponseTO>(storyService, Logger, Mapper)
    { }
}
