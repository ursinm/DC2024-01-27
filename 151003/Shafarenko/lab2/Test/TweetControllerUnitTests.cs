using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.JsonPatch.Operations;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using Moq;
using REST.Controllers.V1_0;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Implementation;
using REST.Storage.Common;
using REST.Storage.InMemoryDb;
using Microsoft.Extensions.Logging;

namespace Test
{
    [TestClass]
    public class TweetControllerUnitTests
    {
        private readonly Mock<ILogger<NewsController>> _loggerMock = new();
        private readonly DbStorage _context = new InMemoryDbContext();
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["REST"]);
        }).CreateMapper();

        private readonly NewsService _tweetService;
        private readonly NewsController _tweetController;
        private CreatorResponseTO _author = null!;

        public TweetControllerUnitTests()
        {
            _tweetService = new NewsService(_context, _mapper);
            _tweetController = new NewsController(_tweetService, _loggerMock.Object, _mapper)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };
        }

        [TestInitialize]
        public async Task TestInitialize()
        {
            var authorLoggerMock = new Mock<ILogger<CreatorController>>();
            var authorService = new CreatorService(_context, _mapper);
            var authorController = new CreatorController(authorService, authorLoggerMock.Object, _mapper)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };
            var authorList = (authorController.Read().Value as List<CreatorResponseTO>)!;
            if (authorList.Count == 0)
            {
                _author = ((await authorController.Create(new(0, "tweetTestAuthorName", "password", "fname", "lname"))).Value
                    as CreatorResponseTO)!;
            }
            else
            {
                _author = authorList[0];
            }

            _context.ChangeTracker.Clear();
        }

        [TestMethod]
        public void GetAll()
        {
            var result = _tweetController.Read();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(List<NewsResponseTO>));
        }

        [TestMethod]
        public async Task Delete()
        {
            var tweetRequest = new NewsRequestTO(0, _author.Id, "tweetDeleteTestTitle", "content", DateTime.Now, 
                DateTime.Now);
            var tweetResponse = (await _tweetController.Create(tweetRequest)).Value as NewsResponseTO;
            Assert.IsNotNull(tweetResponse);

            _context.ChangeTracker.Clear();

            var result = await _tweetController.Delete(tweetResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

        [TestMethod]
        public async Task Create()
        {
            var expected = "testCreateContentTitle";

            var result = 
                (await _tweetController.Create(new(0, _author.Id, expected, "content", DateTime.Now, DateTime.Now))).Value
                as NewsResponseTO;
            Assert.IsNotNull(result);

            Assert.AreEqual(expected, result.Title);
        }

        [TestMethod]
        public async Task Update()
        {
            var expected = "testedUpdateTweetTitle";
            var tweetResponse = (await _tweetController.Create(new(0, _author.Id, "testUpdateTweetTitle", "content", 
                DateTime.Now, DateTime.Now))).Value as NewsResponseTO;
            Assert.IsNotNull(tweetResponse);

            _context.ChangeTracker.Clear();

            var result = (await _tweetController.Update(new(tweetResponse.Id, _author.Id, expected, "content", DateTime.Now, 
                DateTime.Now))).Value as NewsResponseTO;
            Assert.IsNotNull(result);

            Assert.AreEqual(expected, result.Title);
        }

        [TestMethod]
        public async Task Patch()
        {
            var expected = "newPatchTweetTestContent";
            var tweetResponse = (await _tweetController.Create(new(0, _author.Id, "patchTweetTestTitle", expected, 
                DateTime.Now, DateTime.Now))).Value as NewsResponseTO;
            Assert.IsNotNull(tweetResponse);

            var patch = new JsonPatchDocument<News>();
            var addOperation = new Operation<News>
            {
                op = "add",
                path = "/Content",
                value = expected
            };
            patch.Operations.Add(addOperation);

            _context.ChangeTracker.Clear();

            var result = (await _tweetController.PartialUpdate(tweetResponse.Id, patch)).Value as NewsResponseTO;
            Assert.IsNotNull(result);

            Assert.AreEqual(expected, result.Content);
        }
    }
}
