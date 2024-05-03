using DC_Lab1.DTO;
using DC_Lab1;
using DC_Lab1.DTO.Interface;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DC_Lab1.DB;

namespace Test
{
    [TestClass]
    public class PostControllerUnitTest
    {
        private readonly PostController _controller;
        private readonly PostsService _service;
        private readonly PostgreSQlDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab2"]);
        }).CreateMapper();

        private TweetResponseTo Tweet = null!;
        public PostControllerUnitTest()
        {
            _dbContext = new PostgreSQlDbContext();
            _service = new PostsService(_mapper, _dbContext);
            _controller = new PostController(_service)
            { ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() } };
        }

        [TestInitialize]
        public async Task TestInitialize()
        {
            var AuthorService = new AuthorService(_mapper, _dbContext);
            var AuthorController = new AuthorController(AuthorService)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };

            var Authors = (AuthorController.GetAuthors().Value as IEnumerable<AuthorResponseTo>)!;
            AuthorResponseTo Author;
            if (Authors.Count() == 0)
            {
                Author = ((await AuthorController.CreateAuthor(new(0, "TestAuthor", "TestPassword", "TestFirstName", "TestLastName"))).Value as AuthorResponseTo)!;
            }
            else
            {
                Author = Authors.FirstOrDefault()!;
            }

            var TweetService = new TweetService(_mapper, _dbContext);
            var TweetController = new TweetController(TweetService)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };
            var TweetList = (TweetController.GetTweets().Value as IEnumerable<TweetResponseTo>)!;
            if (TweetList.Count() == 0)
            {
                Tweet = ((await TweetController.CreateTweet(new(0, Author!.Id, "PostTestTitle", "content"))).Value as TweetResponseTo)!;
            }
            else
            {
                Tweet = TweetList.FirstOrDefault()!;
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

