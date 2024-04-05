using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using DC_Lab1;
using DC_Lab1.Models;
using DC_Lab1.Services.Interfaces;
using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using DC_Lab1.Services;
using Microsoft.AspNetCore.Components.Forms;

namespace DC_Lab1.Controllers
{
    [ApiController]
    [Route("/api/v1.0/posts")]
    public class PostController(IPostService PostService) : Controller
    {
        [HttpGet]
        public JsonResult GetPosts()
        {
            try
            {
                var Posts = PostService.GetAllEnt();
                return Json(Posts);
            }
            catch
            {
                Response.StatusCode = 403;

                return Json(BadRequest());

            }

        }

        [HttpGet]
        [Route("{PostId:int}")]
        public async Task<JsonResult> GetPostById(int PostId)
        {
            try
            {
                var Post = await PostService.GetEntById(PostId);
                return Json(Post);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }


        }

        [HttpPost]
        public async Task<JsonResult> CreatePost(PostRequestTo PostTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Post = await PostService.CreateEnt(PostTo);
                return Json(Post);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdatePost(PostRequestTo PostTo)
        {
            IResponseTo newPost;
            try
            {
                newPost = await PostService.UpdateEnt(PostTo);
                Response.StatusCode = 200;
                return Json(newPost);

            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{PostId}")]
        public async Task<IActionResult> DeletePost(int PostId)
        {
            try
            {
                Response.StatusCode = 204;
                await PostService.DeleteEnt(PostId);
            }
            catch
            {
                Response.StatusCode = 401;
                return BadRequest();
            }

            return NoContent();



        }
    }
}
