using Microsoft.AspNetCore.Mvc;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;
using REST.Services;
using REST.Services.interfaces;

namespace REST.Controllers;

[Route("api/v1.0/[controller]")]
[ApiController]
public class LabelsController : ControllerBase
{
    private readonly ILabelService _labelService;

    public LabelsController(ILabelService labelService)
    {
        _labelService = labelService;
    }
    // GET: api/v1.0/User
    [HttpGet]
    public async Task<ActionResult<IEnumerable<LabelResponseTo>>> GetAllLabels()
    {
        var users = await _labelService.GetAllAsync();
        /*if (!users.Any())
        {
            return NotFound(new { Error = "Entity not found" });
        }*/
        return Ok(users);
    }

    // GET: api/v1.0/User/5
    [HttpGet("{id}")]
    public async Task<ActionResult<LabelResponseTo>> GetById([FromRoute]int id)
    {
        var user = await _labelService.GetByIdAsync(id);
        return Ok(user);
        /*try
        {
            var user = await _labelService.GetLabelByIdAsync(id);
            return Ok(user);
        }
        catch (Exception ex)
        {
            return NotFound(new { Error = "Entity not found" });
        }*/
    }

    // POST: api/v1.0/User
    [HttpPost]
    public async Task<ActionResult<LabelResponseTo>> CreateLabel(LabelRequestTo label)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }
        var labelAdded = await _labelService.AddAsync(label);
        return CreatedAtAction(nameof(GetById), new { id =labelAdded.id  }, labelAdded);
    }

    // PUT: api/v1.0/User/5
    [HttpPut]
    public async Task<ActionResult<LabelResponseTo>> UpdateLabel(LabelRequestTo label)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }
        try
        {
            var labelUpdate = await _labelService.UpdateAsync(label);
            return Ok(labelUpdate);
        }
        catch (Exception ex)
        {
            // Обработка ошибок, если обновление не удалось
            return NotFound(new { Error = "Entity not found" });
        }
    }

    // DELETE: api/v1.0/User/5
    [HttpDelete("{id}")]
    public async Task<ActionResult<LabelResponseTo>> DeleteLabel(int id)
    {
        try
        {
            await _labelService.DeleteAsync(id);
            return NoContent();
        }
        catch (Exception e)
        {
            return NotFound(new { Error = "Entity not found" });
        }
    }
}