using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WebApplicationDC1.Controllers;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Services.Interfaces;
using Xunit;

namespace RestAPITests
{
    public class StoryControllerTests
    {
        private readonly Mock<IStoryService> _storyServiceMock;
        private readonly Mock<ILogger<StoryController>> _loggerMock;
        private readonly StoryController _controller;

        public StoryControllerTests()
        {
            _storyServiceMock = new Mock<IStoryService>();
            _loggerMock = new Mock<ILogger<StoryController>>();
            _controller = new StoryController(_storyServiceMock.Object, _loggerMock.Object);
        }

        [Fact]
        public void Get_WhenCalled_ReturnsAllStories()
        {
        }

        [Fact]
        public async Task Create_WhenCalled_CreatesNewStory()
        {
        }
    }
}
