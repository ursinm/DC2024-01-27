using Microsoft.AspNetCore.Mvc;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;
using REST.Services;
using REST.Services.interfaces;

namespace REST.Controllers;

[Route("api/v1.0/[controller]")]
[ApiController]
public class PostsController : ControllerBase
{
    private readonly IPostService _postService;

    public PostsController(IPostService postService)
    {
        _postService = postService;
    }
    // GET: api/v1.0/User
    [HttpGet]
    public async Task<ActionResult<IEnumerable<PostResponseTo>>> GetAllPosts()
    {
        var posts = await _postService.GetAllAsync();
        /*if (!users.Any())
        {
            return NotFound(new { Error = "Entity not found" });
        }*/
        return Ok(posts);
    }

    // GET: api/v1.0/User/5
    [HttpGet("{id}")]
    public async Task<ActionResult<PostResponseTo>> GetPostById([FromRoute]long id)
    {
        var posts = await _postService.GetByIdAsync(id);
        return Ok(posts);
        /*try
        {
            var user = await _userService.GetUserByIdAsync(id);
            return Ok(user);
        }
        catch (Exception ex)
        {
            return NotFound(new { Error = "Entity not found" });
            return Ok();
        }*/


    }

    // POST: api/v1.0/User
    [HttpPost]
    public async Task<ActionResult<PostResponseTo>> CreatePost([FromBody]PostRequestTo post)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }
        var postAdded = await _postService.AddAsync(post);
        return CreatedAtAction(nameof(GetPostById), new { id =postAdded.id  }, postAdded);
    }

    // PUT: api/v1.0/User/5
    [HttpPut]
    public async Task<ActionResult<PostResponseTo>> UpdatePost(PostRequestTo post)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }
        try
        {
            var postUpdate = await _postService.UpdateAsync(post);
            return Ok(postUpdate);
        }
        catch (Exception ex)
        {
            // Обработка ошибок, если обновление не удалось
            return NotFound(new { Error = "Entity not found" });
        }
        
    }

    // DELETE: api/v1.0/User/5
    [HttpDelete("{id}")]
    public async Task<ActionResult<PostResponseTo>> DeletePost(int id)
    {
        try
        {
            await _postService.DeleteAsync(id);
            return NoContent();
        }
        catch (Exception e)
        {
            return NotFound(new { Error = "Entity not found" });
        }
        
    }
}