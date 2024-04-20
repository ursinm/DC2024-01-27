using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Lab2;
using Lab2.Models;
using Lab2.DTO;
using Lab2.Services;
using Lab2.Services.Interfaces;
using Lab2.DTO.Interface;
using Microsoft.AspNetCore.Components.Forms;

namespace Lab2.Controllers
{
    [ApiController]
    [Route("/api/v1.0/tweets")]
    public class TweetController(ITweetService TweetService) : Controller
    {
        [HttpGet]
        public JsonResult GetTweets()
        {
            try
            {
                var Tweets = TweetService.GetAllEnt();
                return Json(Tweets);
            }
            catch
            {
                return Json(BadRequest());

            }

        }

        [HttpGet]
        [Route("{tweetId:int}")]
        public async Task<JsonResult> GetTweetById(int tweetId)
        {
            try
            {
                var Tweet = await TweetService.GetEntById(tweetId);
                return Json(Tweet);
            }
            catch
            {
                return Json(BadRequest());
            }


        }

        [HttpPost]
        public async Task<JsonResult> CreateTweet(TweetRequestTo TweetTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Tweet = await TweetService.CreateEnt(TweetTo);
                return Json(Tweet);
            }
            catch
            {
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateTweet(TweetRequestTo TweetTo)
        {
            IResponseTo newTweet;
            try
            {
                newTweet = await TweetService.UpdateEnt(TweetTo);
                Response.StatusCode = 200;
                return Json(newTweet);

            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{tweetId}")]
        public async Task<IActionResult> DeleteTweet(int tweetId)
        {
            try
            {
                Response.StatusCode = 204;
                await TweetService.DeleteEnt(tweetId);
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
