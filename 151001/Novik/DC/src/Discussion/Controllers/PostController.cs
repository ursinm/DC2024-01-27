using Discussion.Models.DTOs.Requests;
using Discussion.Models.DTOs.Responses;
using Discussion.Services.interfaces;
using Microsoft.AspNetCore.Mvc;

namespace Discussion.Controllers;


[ApiController]
[Route("api/v1.0/[controller]")]
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

        try
        {
            var acceptLanguage = Request.Headers["Accept-Language"];
            var firstLanguage = acceptLanguage.ToString().Split(',').FirstOrDefault();
            var postAdded = await _postService.AddAsync(post, firstLanguage);
            return CreatedAtAction(nameof(GetPostById), new { id =postAdded.id  }, postAdded);
        }
        /*catch (DbUpdateException ex)
        {
            return StatusCode(403,new { Error = "IDK" });
        }*/
        catch (Exception e)
        {
            return BadRequest();
        }
           
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
            var acceptLanguage = Request.Headers["Accept-Language"];
            var firstLanguage = acceptLanguage.ToString().Split(',').FirstOrDefault();
     
            var postUpdate = await _postService.UpdateAsync(post, firstLanguage);
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
    public async Task<ActionResult<PostResponseTo>> DeletePost(long id)
    {
        try
        {
            await _postService.DeleteAsync(id);
            return NoContent();
        }
        catch (System.Exception e)
        {
            return NotFound(new { Error = "Entity not found" });
        }
        
    }
}