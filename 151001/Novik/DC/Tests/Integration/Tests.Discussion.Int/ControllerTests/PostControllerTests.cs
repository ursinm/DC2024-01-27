using System.Net;
using System.Net.Http.Json;
using Discussion.Models.DTOs.Requests;
using Discussion.Models.DTOs.Responses;
using Microsoft.AspNetCore.Mvc;
using Moq;
using Newtonsoft.Json;
using Publisher.Controllers;
using Tests.Discussion.Factory;

namespace Tests.Discussion.ControllerTests;

public class PostsControllerTests : IDisposable
{
    private CustomWebApplicationFactory _factory;
    private HttpClient _client;

    public PostsControllerTests()
    {
        _factory = new CustomWebApplicationFactory();
        _client = _factory.CreateClient();
    }

    [Fact]
    public async Task GetAll_ShouldReturnAllEntities()
    {
        var model = new List<PostResponseTo>
        {
            new PostResponseTo() { id = 1, content = "User 1" },
            new PostResponseTo() { id = 2, content = "User 2" }
        };

        var response = await _client.GetAsync("api/v1.0/Posts");

        var data = JsonConvert.DeserializeObject<IEnumerable<PostResponseTo>>(await response.Content.ReadAsStringAsync());
        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        Assert.Collection(data!,
            p => 
            {
                Assert.Equal(1,p.id);
                Assert.Equal("User 1", p.content);
            },
            p =>
            {
                Assert.Equal(2, p.id);
                Assert.Equal("User 2",p.content);
            }
        );

    }
    [Fact]
    public async Task GetPostById_ShouldReturnSingleEntity()
    {
        var postRequest = new PostRequestTo {newsId = 1, content = "Post Content" };
        var response = await _client.PostAsJsonAsync("api/v1.0/Posts", postRequest);
        var data = JsonConvert.DeserializeObject<PostResponseTo>(await response.Content.ReadAsStringAsync());
        var stringpost = "api/v1.0/Posts/" + data.id.ToString();
        
        response = await _client.GetAsync(stringpost);
        data = JsonConvert.DeserializeObject<PostResponseTo>(await response.Content.ReadAsStringAsync());
        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        Assert.Equal(postRequest.newsId, data.newsId);
        Assert.Equal(postRequest.content, data.content);
        response = await _client.DeleteAsync(stringpost);
    }

    [Fact]
    public async Task CreatePost_ShouldReturnCreatedPost()
    {
        var postRequest = new PostRequestTo { newsId = 1, content = "New Post Content" };
        var expectedPostResponse = new PostResponseTo { newsId = 1, content = "New Post Content" };
        _client.DefaultRequestHeaders.Add("Accept-Language", "ru");

        var response = await _client.PostAsJsonAsync("api/v1.0/Posts", postRequest);

        var data = JsonConvert.DeserializeObject<PostResponseTo>(await response.Content.ReadAsStringAsync());
        Assert.Equal(HttpStatusCode.Created, response.StatusCode);
        Assert.Equal(expectedPostResponse.newsId, data.newsId);
        Assert.Equal(expectedPostResponse.content, data.content);
    }

    [Fact]
    public async Task UpdatePost_ShouldReturnUpdatedPost()
    {
        var postRequest = new PostRequestTo {newsId = 1, content = "Post Content" };
        var response = await _client.PostAsJsonAsync("api/v1.0/Posts", postRequest);

        var data = JsonConvert.DeserializeObject<PostResponseTo>(await response.Content.ReadAsStringAsync());
        postRequest = new PostRequestTo()
        {
            id = data.id, newsId = data.newsId, content = "Updated Post Content"
        };
        var expectedPostResponse = new PostResponseTo { id = data.id, newsId = data.newsId, content = "Updated Post Content" };
        response = await _client.PutAsJsonAsync("api/v1.0/Posts", postRequest);

        data = JsonConvert.DeserializeObject<PostResponseTo>(await response.Content.ReadAsStringAsync());
        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        Assert.Equal(expectedPostResponse.id, data.id);
        Assert.Equal(expectedPostResponse.content, data.content);
    }

    [Fact]
    public async Task DeletePost_ShouldReturnNoContent()
    {
        //_factory.postServiceMock.Setup(s => s.DeleteAsync(1)).Returns(Task.CompletedTask);
        var postRequest = new PostRequestTo {newsId = 1, content = "Post Content" };
        var response = await _client.PostAsJsonAsync("api/v1.0/Posts", postRequest);

        var data = JsonConvert.DeserializeObject<PostResponseTo>(await response.Content.ReadAsStringAsync());
        var deletePost = "api/v1.0/Posts/" + data.id.ToString();
        response = await _client.DeleteAsync(deletePost);

        Assert.Equal(HttpStatusCode.NoContent, response.StatusCode);
    }
    public void Dispose()
    {
        _factory.Dispose();
        _client.Dispose();
    }
}