using Asp.Versioning;
using DC.DTO.RequestDTO;
using DC.Services.Impementations;
using DC.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace DC.Controllers.V1
{
	[Route("api/v{version:apiVersion}/notes")]
	[ApiController]
	[ApiVersion("1.0")]
	public class NoteController : ControllerBase
	{
		private readonly INoteService _noteService;

		public NoteController(INoteService noteService)
		{
			_noteService = noteService;
		}

		[HttpGet]
		public async Task<IActionResult> GetPosts()
		{
			var posts = await _noteService.GetNotesAsync();
			return Ok(posts);
		}

		[HttpGet("{id}")]
		public async Task<IActionResult> GetNoteById(long id)
		{
			var post = await _noteService.GetNoteByIdAsync(id);
			return Ok(post);
		}

		[HttpPost]
		public async Task<IActionResult> CreatePost([FromBody] NoteRequestDto note)
		{
			var createdNote = await _noteService.CreateNoteAsync(note);
			return CreatedAtAction(nameof(GetNoteById), new { id = createdNote.Id }, createdNote);
		}

		[HttpPut]
		public async Task<IActionResult> UpdateNote([FromBody] NoteRequestDto note)
		{
			var updatedNote = await _noteService.UpdateNoteAsync(note);
			return Ok(updatedNote);
		}

		[HttpDelete("{id}")]
		public async Task<IActionResult> DeleteNote(long id)
		{
			await _noteService.DeleteNoteAsync(id);
			return NoContent();
		}
	}
}
