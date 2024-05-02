using lab_1.Dtos.RequestDtos;
using lab_1.Dtos.ResponseDtos;
using lab_1.Services;
using Microsoft.AspNetCore.Mvc;

namespace lab_1.Controllers
{
    [ApiController]
    [Route("api/v1.0/storys")]
    public class StorysController : ControllerBase
    {
        private BaseService<StoryRequestDto,StoryResponseDto> storyService;
        public StorysController(BaseService<StoryRequestDto,StoryResponseDto> storyService)
        {
            this.storyService = storyService;
        }
        [HttpGet]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public ActionResult<List<StoryResponseDto>> GetStorys() => Ok(storyService.GetAll());

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public ActionResult<StoryResponseDto> CreateStory([FromBody]StoryRequestDto dto) => CreatedAtAction("CreateStory", storyService.Create(dto));
        [HttpDelete("{id}")]

        [ProducesResponseType(StatusCodes.Status204NoContent)]
        public ActionResult DeleteStory(long id)
        {
            return storyService.Delete(id)?NoContent():NotFound(); 
        }

        [HttpPut]
        public ActionResult<StoryResponseDto> UpdateStory([FromBody]StoryRequestDto dto)
        {

            return storyService.Update(dto) == null ? NotFound(dto) : Ok(dto);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public ActionResult<CommentResponseDto> GetStory(long id) => Ok(storyService.Read(id));

    }
}
