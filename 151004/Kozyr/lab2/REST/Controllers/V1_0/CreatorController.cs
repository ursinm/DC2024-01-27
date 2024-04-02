using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using REST.Controllers.V1_0.Common;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using System.Net;

namespace REST.Controllers.V1_0
{
    [Route("api/v1.0/creators")]
    [ApiController]
    public class CreatorController(ICreatorService CreatorService, ILogger<CreatorController> Logger, IMapper Mapper) :
        AbstractController<Creator, CreatorRequestTO, CreatorResponseTO>(CreatorService, Logger, Mapper)
    { }
}
