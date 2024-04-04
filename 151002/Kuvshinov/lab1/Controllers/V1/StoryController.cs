using Asp.Versioning;
using AutoMapper;
using DC.DTO.RequestDTO;
using DC.Infrastructure.Validators;
using DC.Repositories.Interfaces;
using DC.Services.Impementations;
using DC.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace DC.Controllers.V1
{
	[Route("api/v{version:apiVersion}/storys")]
	[ApiController]
	[ApiVersion("1.0")]
	public class StoryController : ControllerBase
	{
		private readonly IStoryService _storyService;

		public StoryController(IStoryService storyService)
		{
			_storyService = storyService;
		}

		[HttpGet]
		public async Task<IActionResult> GetStories()
		{
			var storys = await _storyService.GetStorysAsync();
			return Ok(storys);
		}

		[HttpGet("{id}")]
		public async Task<IActionResult> GetStoryById(long id)
		{
			var story = await _storyService.GetStoryByIdAsync(id);
			return Ok(story);
		}

		[HttpPost]
		public async Task<IActionResult> CreateStory([FromBody] StoryRequestDto story)
		{
			var createdStory = await _storyService.CreateStoryAsync(story);
			return CreatedAtAction(nameof(GetStoryById), new { id = createdStory.Id }, createdStory);
		}

		[HttpPut]
		public async Task<IActionResult> UpdateStory([FromBody] StoryRequestDto story)
		{
			var updatedStory = await _storyService.UpdateStoryAsync(story);
			return Ok(updatedStory);
		}

		[HttpDelete("{id}")]
		public async Task<IActionResult> DeleteStory(long id)
		{
			await _storyService.DeleteStoryAsync(id);
			return NoContent();
		}
	}
}
