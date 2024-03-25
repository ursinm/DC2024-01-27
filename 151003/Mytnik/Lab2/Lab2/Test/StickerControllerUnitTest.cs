using DC_Lab1.DTO.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test
{
    public class MarkerControllerUnitTest
    {
        private readonly MarkerController _controller;
        private readonly MarkerService _service;
        private readonly SqLiteDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();
        public MarkerControllerUnitTest()
        {
            _dbContext = new SqLiteDbContext();
            _service = new MarkerService(_mapper, _dbContext);
            _controller = new MarkerController(_service)
            { ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() } };


        }

        [TestMethod]
        public void GetUpdate()
        {
            var MarkerRequest = new MarkerRequestTo(0,  "TestName");
            var Marker = _controller.CreateMarker(MarkerRequest).Result.Value as MarkerResponseTo;
            Assert.IsNotNull(Marker);

            var newMarker = new MarkerRequestTo(Marker.Id, "NewTestName");
            _dbContext.ChangeTracker.Clear();
            var MarkerResponce = _controller.UpdateMarker(newMarker).Result.Value as MarkerResponseTo;
            Assert.IsNotNull(MarkerResponce);
            Assert.AreEqual(MarkerResponce.Name, newMarker.Name);
        }
        [TestMethod]
        public void GetEntById()
        {
            var MarkerRequest = new MarkerRequestTo(0, "TestName");
            var Marker = _controller.CreateMarker(MarkerRequest).Result.Value as MarkerResponseTo;
            Assert.IsNotNull(Marker);
            _dbContext.ChangeTracker.Clear();
            var foundedMarker = _controller.GetMarkerById(Marker.Id).Result.Value as MarkerResponseTo;
            Assert.IsNotNull(foundedMarker);
            Assert.AreEqual(foundedMarker.Id, Marker.Id);
        }
        [TestMethod]
        public async Task GetAllEnt()
        {

            var result = _controller.GetMarkers();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(IEnumerable<IResponseTo>));
        }
        [TestMethod]
        public async Task CreateEnt()
        {
            var MarkerRequest = new MarkerRequestTo(0, "TestName");
            var MarkerResponse = (await _controller.CreateMarker(MarkerRequest)).Value as MarkerResponseTo;
            Assert.IsNotNull(MarkerResponse);

            Assert.AreEqual(MarkerResponse.Name, "TestName");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var MarkerRequest = new MarkerRequestTo(0, "TestName");
            var MarkerResponse = (await _controller.CreateMarker(MarkerRequest)).Value as MarkerResponseTo;
            Assert.IsNotNull(MarkerResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeleteMarker(MarkerResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}
