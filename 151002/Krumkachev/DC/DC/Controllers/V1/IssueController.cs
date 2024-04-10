using Asp.Versioning;
using DC.DTO.RequestDTO;
using DC.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace DC.Controllers.V1
{
	[Route("api/v{version:apiVersion}/issues")]
	[ApiController]
	[ApiVersion("1.0")]
	public class IssueController(IIssueService issueService) : ControllerBase
	{
		private readonly IIssueService _issueService = issueService;

		[HttpGet]
		public async Task<IActionResult> GetIssues()
		{
			var issues = await _issueService.GetIssuesAsync();
			return Ok(issues);
		}

		[HttpGet("{id}")]
		public async Task<IActionResult> GetIssueById(long id)
		{
			var issue = await _issueService.GetIssueByIdAsync(id);
			return Ok(issue);
		}

		[HttpPost]
		public async Task<IActionResult> CreateIssue([FromBody] IssueRequestDto issue)
		{
			var createdIssue = await _issueService.CreateIssueAsync(issue);
			return CreatedAtAction(nameof(GetIssueById), new { id = createdIssue.Id }, createdIssue);
		}

		[HttpPut]
		public async Task<IActionResult> UpdateIssue([FromBody] IssueRequestDto issue)
		{
			var updatedIssue = await _issueService.UpdateIssueAsync(issue);
			return Ok(updatedIssue);
		}

		[HttpDelete("{id}")]
		public async Task<IActionResult> DeleteIssue(long id)
		{
			await _issueService.DeleteIssueAsync(id);
			return NoContent();
		}
	}
}
