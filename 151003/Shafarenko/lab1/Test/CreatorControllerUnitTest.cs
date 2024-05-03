using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Moq;
using REST.Controllers.V1_0;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using System.Net;

namespace Test
{
    [TestClass]
    public class CreatorControllerUnitTest
    {
        [TestMethod]
        public async Task GetByTweetID_ReturnsJsonResult()
        {
            int tweetId = 1;
            var expectedResponse = new CreatorResponseTO(1, "login", "fname", "lname");

            var creatorServiceMock = new Mock<ICreatorService>();
            creatorServiceMock.Setup(service => service.GetByNewsID(tweetId))
                .ReturnsAsync(expectedResponse);

            var loggerMock = new Mock<ILogger<CreatorController>>();

            var controller = new CreatorController(creatorServiceMock.Object, loggerMock.Object);

            var result = await controller.GetByNewsID(tweetId);

            Assert.IsNotNull(result);
            Assert.AreEqual(expectedResponse, result.Value);
        }

        [TestMethod]
        public async Task GetByTweetID_ReturnsBadRequest_WhenExceptionThrown()
        {
            int tweetId = 1;
            var expectedException = new Exception("Some error message");

            var creatorServiceMock = new Mock<ICreatorService>();
            creatorServiceMock.Setup(service => service.GetByNewsID(tweetId))
                .ThrowsAsync(expectedException);

            var loggerMock = new Mock<ILogger<CreatorController>>();

            var controller = new CreatorController(creatorServiceMock.Object, loggerMock.Object)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };

            var result = await controller.GetByNewsID(tweetId);

            Assert.AreEqual((int)HttpStatusCode.BadRequest, controller.Response.StatusCode);
        }
    }
}