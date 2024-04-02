using DC_Lab1.DTO;
using DC_Lab1;
using DC_Lab1.DTO.Interface;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test
{
    [TestClass]
    public class NoteControllerUnitTest
    {
        private readonly NoteController _controller;
        private readonly NotesService _service;
        private readonly SqLiteDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();

        private TweetResponseTo Tweet = null!;
        public NoteControllerUnitTest()
        {
            _dbContext = new SqLiteDbContext();
            _service = new NotesService(_mapper, _dbContext);
            _controller = new NoteController(_service)
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
            CreatorResponseTo Creator;
            if (Creators.Count() == 0)
            {
                Creator = ((await CreatorController.CreateCreator(new(0, "TestCreator", "TestPassword", "TestFirstName", "TestLastName"))).Value as CreatorResponseTo)!;
            }
            else
            {
                Creator = Creators.FirstOrDefault()!;
            }

            var tweetService = new TweetService(_mapper, _dbContext);
            var tweetController = new TweetController(tweetService)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };
            var tweetList = (tweetController.GetTweets().Value as IEnumerable<TweetResponseTo>)!;
            if (tweetList.Count() == 0)
            {
                Tweet = ((await tweetController.CreateTweet(new(0, Creator!.Id, "NoteTestTitle", "content"))).Value as TweetResponseTo)!;
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
            var NoteRequest = new NoteRequestTo(0, Tweet.Id,  "TestContent");
            var Note = _controller.CreateNote(NoteRequest).Result.Value as NoteResponseTo;
            Assert.IsNotNull(Note);

            var newNote = new NoteRequestTo(Note.Id, Tweet.Id,  "newTestContent");
            _dbContext.ChangeTracker.Clear();
            var NoteResponce = _controller.UpdateNote(newNote).Result.Value as NoteResponseTo;
            Assert.IsNotNull(NoteResponce);
            Assert.AreEqual(NoteResponce.Content, newNote.Content);
        }
        [TestMethod]
        public void GetEntById()
        {
            var NoteRequest = new NoteRequestTo(0, Tweet.Id, "TestContent");
            var Note = _controller.CreateNote(NoteRequest).Result.Value as NoteResponseTo;
            Assert.IsNotNull(Note);
            _dbContext.ChangeTracker.Clear();
            var foundedNote = _controller.GetNoteById(Note.Id).Result.Value as NoteResponseTo;
            Assert.IsNotNull(foundedNote);
            Assert.AreEqual(foundedNote.Id, Note.Id);
        }
        [TestMethod]
        public void GetAllEnt()
        {
            var result = _controller.GetNotes();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(IEnumerable<IResponseTo>));
        }
        [TestMethod]
        public async Task CreateEnt()
        {

            var NoteRequest = new NoteRequestTo(0, Tweet.Id,  "TestContent");
            var NoteResponse = (await _controller.CreateNote(NoteRequest)).Value as NoteResponseTo;
            Assert.IsNotNull(NoteResponse);

            Assert.AreEqual(NoteResponse.Content, "TestContent");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var NoteRequest = new NoteRequestTo(0, Tweet.Id,"TestContent");
            var NoteResponse = (await _controller.CreateNote(NoteRequest)).Value as NoteResponseTo;
            Assert.IsNotNull(NoteResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeleteNote(NoteResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}

