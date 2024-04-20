using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using WebApplicationDC1.Controllers;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Services.Interfaces;

namespace RestAPITests
{
    [TestClass]
    public class CreatorControllerUnitTest
    {
        [TestMethod]
        public async Task GetBystoryID_ReturnsJsonResult()
        {
            int storyId = 1;
            var expectedResponse = new CreatorResponseTO(1, "login", "fname", "lname");

            var CreatorServiceMock = new Mock<ICreatorService>();
            CreatorServiceMock.Setup(service => service.GetByStoryID(storyId))
                .ReturnsAsync(expectedResponse);

            var loggerMock = new Mock<ILogger<CreatorController>>();

            var controller = new CreatorController(CreatorServiceMock.Object, loggerMock.Object);

            var result = await controller.GetByStoryID(storyId);

            Assert.IsNotNull(result);
            Assert.AreEqual(expectedResponse, result.Value);
        }

        [TestMethod]
        public async Task GetBystoryID_ReturnsBadRequest_WhenExceptionThrown()
        {
            int storyId = 1;
            var expectedException = new Exception("Some error message");

            var CreatorServiceMock = new Mock<ICreatorService>();
            CreatorServiceMock.Setup(service => service.GetByStoryID(storyId))
                .ThrowsAsync(expectedException);

            var loggerMock = new Mock<ILogger<CreatorController>>();

            var controller = new CreatorController(CreatorServiceMock.Object, loggerMock.Object)
            {
                ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() }
            };

            var result = await controller.GetByStoryID(storyId);

            Assert.AreEqual((int)HttpStatusCode.BadRequest, controller.Response.StatusCode);
        }


    }
}
