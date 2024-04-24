using Asp.Versioning;
using DC.DTO.RequestDTO;
using DC.Services.Impementations;
using DC.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace DC.Controllers.V1
{
	[Route("api/v{version:apiVersion}/labels")]
	[ApiController]
	[ApiVersion("1.0")]
	public class LabelController : ControllerBase
	{
		private readonly ILabelService _labelService;

		public LabelController(ILabelService labelService)
		{
			_labelService = labelService;
		}

		[HttpGet]
		public async Task<IActionResult> GetLabels()
		{
			var labels = await _labelService.GetLabelsAsync();
			return Ok(labels);
		}

		[HttpGet("{id}")]
		public async Task<IActionResult> GetLabelById(long id)
		{
			var label = await _labelService.GetLabelByIdAsync(id);
			return Ok(label);
		}

		[HttpPost]
		public async Task<IActionResult> CreateLabel([FromBody] LabelRequestDto label)
		{
			var createdLabel = await _labelService.CreateLabelAsync(label);
			return CreatedAtAction(nameof(GetLabelById), new { id = createdLabel.Id }, createdLabel);
		}

		[HttpPut]
		public async Task<IActionResult> UpdateLabel([FromBody] LabelRequestDto label)
		{
			var updatedLabel = await _labelService.UpdateLabelAsync(label);
			return Ok(updatedLabel);
		}

		[HttpDelete("{id}")]
		public async Task<IActionResult> DeleteLabel(long id)
		{
			await _labelService.DeleteLabelAsync(id);
			return NoContent();
		}
	}
}
