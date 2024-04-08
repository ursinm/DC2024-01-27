using DC_Lab1.DB.BaseDBContext;
using DC_Lab1.DTO.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test
{
    [TestClass]
    public class TweetControllerUnitTest
    {
        private readonly TweetController _controller;
        private readonly TweetService _service;
        private readonly BaseDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();

        private EditorResponseTo editor = null!;
        public TweetControllerUnitTest()
        {
            _dbContext = new BaseDbContext();
            _service = new TweetService(_mapper, _dbContext);
            _controller = new TweetController(_service)
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

            if ( Editors.Count() == 0)
            {
                editor = ((await EditorController.CreateEditor(new(0, "TestEditor", "TestPassword", "TestFirstName", "TestLastName"))).Value as EditorResponseTo)!;
            }
            else
            {
                editor = Editors.FirstOrDefault()!;
            }

            _dbContext.ChangeTracker.Clear();
        }

        [TestMethod]
        public void GetUpdate()
        {
            var TweetRequest = new TweetRequestTo(0, editor.Id, "testTitle", "TestContent");
            var Tweet = _controller.CreateTweet(TweetRequest).Result.Value as TweetResponseTo;
            Assert.IsNotNull(Tweet);

            var newTweet = new TweetRequestTo(Tweet.Id, editor.Id, "newtestTitle", "newTestContent");
            _dbContext.ChangeTracker.Clear();
            var TweetResponce = _controller.UpdateTweet(newTweet).Result.Value as TweetResponseTo;
            Assert.IsNotNull(TweetResponce);
            Assert.AreEqual(TweetResponce.Title, newTweet.Title);
        }
        [TestMethod]
        public void GetEntById()
        {
            var TweetRequest = new TweetRequestTo(0, editor.Id, "testTitle", "TestContent");
            var Tweet = _controller.CreateTweet(TweetRequest).Result.Value as TweetResponseTo;
            Assert.IsNotNull(Tweet);
            _dbContext.ChangeTracker.Clear();
            var foundedTweet = _controller.GetTweetById(Tweet.Id).Result.Value as TweetResponseTo;
            Assert.IsNotNull(foundedTweet);
            Assert.AreEqual(foundedTweet.Id, Tweet.Id);
        }
        [TestMethod]
        public void GetAllEnt()
        {
            var result = _controller.GetTweets();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(IEnumerable<IResponseTo>));
        }
        [TestMethod]
        public async Task CreateEnt()
        {

            var TweetRequest = new TweetRequestTo(0, editor.Id, "testTitle", "TestContent");
            var TweetResponse = (await _controller.CreateTweet(TweetRequest)).Value as TweetResponseTo;
            Assert.IsNotNull(TweetResponse);

            Assert.AreEqual(TweetResponse.Title, "testTitle");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var TweetRequest = new TweetRequestTo(0, editor.Id, "testTitle", "TestContent");
            var TweetResponse = (await _controller.CreateTweet(TweetRequest)).Value as TweetResponseTo;
            Assert.IsNotNull(TweetResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeleteTweet(TweetResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}
