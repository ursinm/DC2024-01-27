using Api.DTO.RequestDTO;
using Api.Services.Interfaces;
using Asp.Versioning;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers.V1
{
    [Route("api/v{version:apiVersion}/news")]
    [ApiController]
    [ApiVersion("1.0")]
    public class NewsController(INewsService newsService) : ControllerBase
    {
        private readonly INewsService _niewsService = newsService;

        [HttpGet]
        public async Task<IActionResult> GetIssues()
        {
            var issues = await _niewsService.GetNewsAsync();
            return Ok(issues);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetIssueById(long id)
        {
            var issue = await _niewsService.GetNewsByIdAsync(id);
            return Ok(issue);
        }

        [HttpPost]
        public async Task<IActionResult> CreateIssue([FromBody] NewsRequestDto issue)
        {
            var createdIssue = await _niewsService.CreateNewsAsync(issue);
            return CreatedAtAction(nameof(GetIssueById), new { id = createdIssue.Id }, createdIssue);
        }

        [HttpPut]
        public async Task<IActionResult> UpdateIssue([FromBody] NewsRequestDto issue)
        {
            var updatedIssue = await _niewsService.UpdateNewsAsync(issue);
            return Ok(updatedIssue);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteIssue(long id)
        {
            await _niewsService.DeleteNewsAsync(id);
            return NoContent();
        }
    }
}
