using lab_1.Dtos.RequestDtos;
using lab_1.Dtos.ResponseDtos;
using lab_1.Services;
using Microsoft.AspNetCore.Mvc;

namespace lab_1.Controllers
{
    [ApiController]
    [Route("api/v1.0/markers")]
    public class MarkersController : ControllerBase
    {
        private BaseService<MarkerRequestDto,MarkerResponseDto> authorService;
        public MarkersController(BaseService<MarkerRequestDto,MarkerResponseDto> authorService)
        {
            this.authorService = authorService;
        }
        [HttpGet]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public ActionResult<List<MarkerResponseDto>> GetAuthors() => Ok(authorService.GetAll());

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public ActionResult<MarkerResponseDto> CreateAuthor([FromBody]MarkerRequestDto dto) => CreatedAtAction("CreateAuthor", authorService.Create(dto));
        [HttpDelete("{id}")]

        [ProducesResponseType(StatusCodes.Status204NoContent)]
        public ActionResult DeleteAuthor(long id)
        {
            return authorService.Delete(id)?NoContent():NotFound(); 
        }

        [HttpPut]
        public ActionResult<MarkerResponseDto> UpdateAuthor([FromBody] MarkerRequestDto dto)
        {

            return authorService.Update(dto) == null ? NotFound(dto) : Ok(dto);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public ActionResult<CommentResponseDto> GetAuthor(long id) => Ok(authorService.Read(id));

    }
}
