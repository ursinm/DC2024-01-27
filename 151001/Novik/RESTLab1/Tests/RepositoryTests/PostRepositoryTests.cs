using REST.Models.Entity;
using REST.Repositories;

namespace Tests.RepositoryTests;

public class PostRepositoryTests
{
    [Test]
    public async Task AddAsync_ShouldAddEntity()
    {
        // Arrange
        var entity = new Post { newsId = 1, content = "Content"};
        var postRep = new PostRepository();
        // Act
        var result = await postRep.AddAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entity));
    }
    [Test]
    public async Task GetByIdAsync_ShouldReturnEntity()
    {
        // Arrange
        var entities = new List<Post> { new Post { newsId = 1, content = "Content"} };
        var postRep = new PostRepository();
        await postRep.AddAsync(entities[0]);

        // Act
        var result = await postRep.GetByIdAsync(1);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entities[0]));
    }
    
    [Test]
    public async Task GetByIdAsync_EntityNotExists()
    {
        // Arrange
        var postRep = new PostRepository();

        // Act
        var result = await postRep.GetByIdAsync(1);

        // Assert
        Assert.That(result, Is.EqualTo(null));
    }

    [Test]
    public async Task GetAllAsync_ShouldReturnAllEntities()
    {
        // Arrange
        var entities = new List<Post>
        {
            new Post { newsId = 1, content = "Content1"},
            new Post { newsId = 1, content = "Content2" }
        };
        var postRep = new PostRepository();
        foreach (var entity in entities)
        {
            await postRep.AddAsync(entity);
        }

        // Act
        var result = await postRep.GetAllAsync();

        // Assert
        Assert.NotNull(result);
        Assert.That(result.Count(), Is.EqualTo(2));
    }
    
    [Test]
    public async Task GetAllAsync_NoEntities()
    {
        // Arrange
        var postRep = new PostRepository();
        // Act
        var result = await postRep.GetAllAsync();
        var expected = new Dictionary<long, Post>();
        // Assert
        Assert.That(result, Is.EqualTo(expected));
    }
    
    [Test]
    public async Task UpdateAsync_ShouldUpdateEntity()
    {
        // Arrange
        var entity = new Post { newsId = 1, content = "Content"};
        var postRep = new PostRepository();
        entity = await postRep.AddAsync(entity);
        entity.content = "Updated Content";
        // Act
        var result = await postRep.UpdateAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result.content, Is.EqualTo("Updated Content"));
    }

    [Test]
    public async Task DeleteAsync_ShouldRemoveEntity()
    {
        var entity = new Post { newsId = 1, content = "Content"};
        // Arrange
        var postRep = new PostRepository();
        await postRep.AddAsync(entity);
        // Act
        Task task =  postRep.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
    
    [Test]
    public async Task DeleteAsync_NoEntities()
    {
        var postRep = new PostRepository();
        Task task =  postRep.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
}