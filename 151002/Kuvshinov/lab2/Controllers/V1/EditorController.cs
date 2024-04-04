using Asp.Versioning;
using DC.DTO.RequestDTO;
using DC.Services.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace DC.Controllers.V1
{
	[Route("api/v{version:apiVersion}/editors")]
	[ApiController]
	[ApiVersion("1.0")]
	public class EditorController : ControllerBase
	{
		public EditorController(IEditorService editorService)
		{
            _editorService = editorService;
		}

		private readonly IEditorService _editorService;

		[HttpGet]
		public async Task<IActionResult> GetEditors()
		{
			var editors = await _editorService.GetEditorsAsync();
			return Ok(editors);
		}

		[HttpGet("{id}")]
		public async Task<IActionResult> GetEditorById(long id)
		{
			var editor = await _editorService.GetEditorByIdAsync(id);
			return Ok(editor);
		}

		[HttpPost]
		public async Task<IActionResult> CreateEditor([FromBody] EditorRequestDto editor)
		{
			var createdEditor = await _editorService.CreateEditorAsync(editor);
			return CreatedAtAction(nameof(GetEditorById), new { id = createdEditor.Id }, createdEditor);
		}

		[HttpPut]
		public async Task<IActionResult> UpdateEditor([FromBody] EditorRequestDto editor)
		{
			var updatedEditor = await _editorService.UpdateEditorAsync(editor);
			return Ok(updatedEditor);
		}

		[HttpDelete("{id}")]
		public async Task<IActionResult> DeleteEditor(long id)
		{
			await _editorService.DeleteEditorAsync(id);
			return NoContent();
		}
	}
}
