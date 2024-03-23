using Asp.Versioning;
using DC.DTO.RequestDTO;
using DC.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace DC.Controllers.V1
{
	[Route("api/v{version:apiVersion}/posts")]
	[ApiController]
	[ApiVersion("1.0")]
	public class PostController(IPostService postService) : ControllerBase
	{
		private readonly IPostService _postService = postService;

		[HttpGet]
		public async Task<IActionResult> GetPosts()
		{
			var posts = await _postService.GetPostsAsync();
			return Ok(posts);
		}

		[HttpGet("{id}")]
		public async Task<IActionResult> GetPostById(long id)
		{
			var post = await _postService.GetPostByIdAsync(id);
			return Ok(post);
		}

		[HttpPost]
		public async Task<IActionResult> CreatePost([FromBody] PostRequestDto post)
		{
			var createdPost = await _postService.CreatePostAsync(post);
			return CreatedAtAction(nameof(GetPostById), new { id = createdPost.Id }, createdPost);
		}

		[HttpPut]
		public async Task<IActionResult> UpdatePost([FromBody] PostRequestDto post)
		{
			var updatedPost = await _postService.UpdatePostAsync(post);
			return Ok(updatedPost);
		}

		[HttpDelete("{id}")]
		public async Task<IActionResult> DeletePost(long id)
		{
			await _postService.DeletePostAsync(id);
			return NoContent();
		}
	}
}
