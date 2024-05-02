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
    {
        [HttpGet]
        [Route("news/{id:int}")]
        public async Task<JsonResult> GetByNewsID([FromRoute] int id)
        {
            CreatorResponseTO? response = null;
            Logger.LogInformation("Getting creator by news ID: {id}", id);

            try
            {
                response = await CreatorService.GetByNewsID(id);
            }
            catch (Exception ex)
            {
                Logger.LogError("Error getting creator by news ID {ex}", ex);
                Response.StatusCode = (int)HttpStatusCode.BadRequest;
            }

            return Json(response);
        }
    }
}
