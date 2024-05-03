using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Publisher.Controllers.V1_0.Common;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Service.Interface;
using System.Net;

namespace Publisher.Controllers.V1_0
{
    [Route("api/v1.0/creators")]
    [ApiController]
    public class creatorController(IcreatorService creatorService, ILogger<creatorController> Logger, IMapper Mapper) :
        AbstractController<creator, creatorRequestTO, creatorResponseTO>(creatorService, Logger, Mapper)
    {
        [HttpGet]
        [Route("storys/{id:int}")]
        public async Task<JsonResult> GetBystoryID([FromRoute] int id)
        {
            creatorResponseTO? response = null;
            Logger.LogInformation("Getting creator by story ID: {id}", id);

            try
            {
                response = await creatorService.GetBystoryID(id);
            }
            catch (Exception ex)
            {
                Logger.LogError("Error getting creator by story ID {ex}", ex);
                Response.StatusCode = (int)HttpStatusCode.BadRequest;
            }

            return Json(response);
        }
    }
}
