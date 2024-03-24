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
        private readonly SqLiteDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();

        private CreatorResponseTo Creator = null!;
        public TweetControllerUnitTest()
        {
            _dbContext = new SqLiteDbContext();
            _service = new TweetService(_mapper, _dbContext);
            _controller = new TweetController(_service)
            { ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() } };


        }

        [TestInitialize]
        public async Task TestInitialize()
        {

            var CreatorService = new CreatorService(_mapper, _dbContext);
            var CreatorController = new CreatorController(CreatorService)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };
          
            var Creators = (CreatorController.GetCreators().Value as IEnumerable<CreatorResponseTo>)!;

            if ( Creators.Count() == 0)
            {
                Creator = ((await CreatorController.CreateCreator(new(0, "TestCreator", "TestPassword", "TestFirstName", "TestLastName"))).Value as CreatorResponseTo)!;
            }
            else
            {
                Creator = Creators.FirstOrDefault()!;
            }

            _dbContext.ChangeTracker.Clear();
        }

        [TestMethod]
        public void GetUpdate()
        {
            var TweetRequest = new TweetRequestTo(0, Creator.Id, "testTitle", "TestContent");
            var Tweet = _controller.CreateTweet(TweetRequest).Result.Value as TweetResponseTo;
            Assert.IsNotNull(Tweet);

            var newTweet = new TweetRequestTo(Tweet.Id, Creator.Id, "newtestTitle", "newTestContent");
            _dbContext.ChangeTracker.Clear();
            var TweetResponce = _controller.UpdateTweet(newTweet).Result.Value as TweetResponseTo;
            Assert.IsNotNull(TweetResponce);
            Assert.AreEqual(TweetResponce.Title, newTweet.Title);
        }
        [TestMethod]
        public void GetEntById()
        {
            var TweetRequest = new TweetRequestTo(0, Creator.Id, "testTitle", "TestContent");
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

            var TweetRequest = new TweetRequestTo(0, Creator.Id, "testTitle", "TestContent");
            var TweetResponse = (await _controller.CreateTweet(TweetRequest)).Value as TweetResponseTo;
            Assert.IsNotNull(TweetResponse);

            Assert.AreEqual(TweetResponse.Title, "testTitle");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var TweetRequest = new TweetRequestTo(0, Creator.Id, "testTitle", "TestContent");
            var TweetResponse = (await _controller.CreateTweet(TweetRequest)).Value as TweetResponseTo;
            Assert.IsNotNull(TweetResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeleteTweet(TweetResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}
