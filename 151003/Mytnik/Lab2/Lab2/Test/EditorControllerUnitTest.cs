using AutoMapper;
using DC_Lab1.DTO.Interface;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations.Operations;

namespace Test
{
    [TestClass]
    public class CreatorControllerUnitTest
    {

        private readonly CreatorController _controller;
        private readonly CreatorService _service;
        private readonly SqLiteDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();
        public CreatorControllerUnitTest()
        { 
            _dbContext = new SqLiteDbContext();
            _service = new CreatorService(_mapper, _dbContext);
            _controller = new CreatorController(_service)
            { ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() } };


        }

        [TestMethod]
        public void GetUpdate()
        {
            var CreatorRequest = new CreatorRequestTo(0,"testLogin", "password", "fname", "lname");
            var Creator = _controller.CreateCreator(CreatorRequest).Result.Value as CreatorResponseTo;
            Assert.IsNotNull(Creator);

            var newCreator = new CreatorRequestTo(Creator.Id,"newTestLogin", "newPassword", "newFname", "newLname");
            _dbContext.ChangeTracker.Clear();
            var CreatorResponce = _controller.UpdateCreator(newCreator).Result.Value as CreatorResponseTo;
            Assert.IsNotNull(CreatorResponce);
            Assert.AreEqual(CreatorResponce.Login, newCreator.Login);
        }
        [TestMethod]
        public void GetEntById()
        {
            var CreatorRequest = new CreatorRequestTo(0,"testLogin", "password", "fname", "lname");
            var Creator = _controller.CreateCreator(CreatorRequest).Result.Value as CreatorResponseTo;
            Assert.IsNotNull(Creator);
            _dbContext.ChangeTracker.Clear();
            var foundedCreator = _controller.GetCreatorById(Creator.Id).Result.Value as CreatorResponseTo;
            Assert.IsNotNull(foundedCreator);
            Assert.AreEqual(foundedCreator.Id, Creator.Id);
        }
        [TestMethod]
        public async Task GetAllEnt()
        {
            
            var result = _controller.GetCreators();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(IEnumerable<IResponseTo>));
        }
        [TestMethod]
        public async Task CreateEnt()
        {
            var CreatorRequest = new CreatorRequestTo(0,"testLogin", "password", "fname", "lname");
            var CreatorResponse = (await _controller.CreateCreator(CreatorRequest)).Value as CreatorResponseTo;
            Assert.IsNotNull(CreatorResponse);

            Assert.AreEqual(CreatorResponse.Login, "testLogin");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var CreatorRequest = new CreatorRequestTo(0, "testDeleteLogin", "password", "fname", "lname");
            var CreatorResponse = (await _controller.CreateCreator(CreatorRequest)).Value as CreatorResponseTo;
            Assert.IsNotNull(CreatorResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeleteCreator(CreatorResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}