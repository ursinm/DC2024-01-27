using DC_Lab1.DTO;
using DC_Lab1;
using DC_Lab1.DTO.Interface;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DC_Lab1.DB.BaseDBContext;

namespace Test
{
    [TestClass]
    public class PostControllerUnitTest
    {
        private readonly PostController _controller;
        private readonly PostService _service;
        private readonly BaseDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();

        private TweetResponseTo Tweet = null!;
        public PostControllerUnitTest()
        {
            _dbContext = new BaseDbContext();
            _service = new PostService(_mapper, _dbContext);
            _controller = new PostController(_service)
            { ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() } };


        }

        [TestInitialize]
        public async Task TestInitialize()
        {

            var EditorService = new EditorService(_mapper, _dbContext);
            var EditorController = new EditorController(EditorService)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };

            var Editors = (EditorController.GetEditors().Value as IEnumerable<EditorResponseTo>)!;
            EditorResponseTo editor;
            if (Editors.Count() == 0)
            {
                editor = ((await EditorController.CreateEditor(new(0, "TestEditor", "TestPassword", "TestFirstName", "TestLastName"))).Value as EditorResponseTo)!;
            }
            else
            {
                editor = Editors.FirstOrDefault()!;
            }

            var tweetService = new TweetService(_mapper, _dbContext);
            var tweetController = new TweetController(tweetService)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };
            var tweetList = (tweetController.GetTweets().Value as IEnumerable<TweetResponseTo>)!;
            if (tweetList.Count() == 0)
            {
                Tweet = ((await tweetController.CreateTweet(new(0, editor!.Id, "postTestTitle", "content"))).Value as TweetResponseTo)!;
            }
            else
            {
                Tweet = tweetList.FirstOrDefault()!;
            }


            _dbContext.ChangeTracker.Clear();
        }

        [TestMethod]
        public void GetUpdate()
        {
            var PostRequest = new PostRequestTo(0, Tweet.Id,  "TestContent");
            var Post = _controller.CreatePost(PostRequest).Result.Value as PostResponseTo;
            Assert.IsNotNull(Post);

            var newPost = new PostRequestTo(Post.Id, Tweet.Id,  "newTestContent");
            _dbContext.ChangeTracker.Clear();
            var PostResponce = _controller.UpdatePost(newPost).Result.Value as PostResponseTo;
            Assert.IsNotNull(PostResponce);
            Assert.AreEqual(PostResponce.Content, newPost.Content);
        }
        [TestMethod]
        public void GetEntById()
        {
            var PostRequest = new PostRequestTo(0, Tweet.Id, "TestContent");
            var Post = _controller.CreatePost(PostRequest).Result.Value as PostResponseTo;
            Assert.IsNotNull(Post);
            _dbContext.ChangeTracker.Clear();
            var foundedPost = _controller.GetPostById(Post.Id).Result.Value as PostResponseTo;
            Assert.IsNotNull(foundedPost);
            Assert.AreEqual(foundedPost.Id, Post.Id);
        }
        [TestMethod]
        public void GetAllEnt()
        {
            var result = _controller.GetPosts();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(IEnumerable<IResponseTo>));
        }
        [TestMethod]
        public async Task CreateEnt()
        {

            var PostRequest = new PostRequestTo(0, Tweet.Id,  "TestContent");
            var PostResponse = (await _controller.CreatePost(PostRequest)).Value as PostResponseTo;
            Assert.IsNotNull(PostResponse);

            Assert.AreEqual(PostResponse.Content, "TestContent");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var PostRequest = new PostRequestTo(0, Tweet.Id,"TestContent");
            var PostResponse = (await _controller.CreatePost(PostRequest)).Value as PostResponseTo;
            Assert.IsNotNull(PostResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeletePost(PostResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}

