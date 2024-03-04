using REST.Models.Entity;
using REST.Repositories;
using REST.Repositories.sample;

namespace Tests.RepositoryTests;

public class NewsRepositoryTests
{
    [Test]
    public async Task AddAsync_ShouldAddEntity()
    {
        // Arrange
        var entity = new News { title = "Title", content = "Content"};
        var news = new NewsRepository();
        // Act
        var result = await news.AddAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entity));
    }
    [Test]
    public async Task GetByIdAsync_ShouldReturnEntity()
    {
        // Arrange
        var entities = new List<News> { new News { title = "Title", content = "Content"} };
        var newsRep = new NewsRepository();
        await newsRep.AddAsync(entities[0]);

        // Act
        var result = await newsRep.GetByIdAsync(1);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entities[0]));
    }
    
    [Test]
    public async Task GetByIdAsync_EntityNotExists()
    {
        // Arrange
        var newsRep = new NewsRepository();

        // Act
        var result = await newsRep.GetByIdAsync(1);

        // Assert
        Assert.That(result, Is.EqualTo(null));
    }

    [Test]
    public async Task GetAllAsync_ShouldReturnAllEntities()
    {
        // Arrange
        var entities = new List<News>
        {
            new News { title = "Title1", content = "Content1"},
            new News { title = "Title2", content = "Content2" }
        };
        var newsRep = new NewsRepository();
        foreach (var entity in entities)
        {
            await newsRep.AddAsync(entity);
        }

        // Act
        var result = await newsRep.GetAllAsync();

        // Assert
        Assert.NotNull(result);
        Assert.That(result.Count(), Is.EqualTo(2));
    }
    
    [Test]
    public async Task GetAllAsync_NoEntities()
    {
        // Arrange
        var newsRep = new NewsRepository();
        // Act
        var result = await newsRep.GetAllAsync();
        var expected = new Dictionary<long, News>();
        // Assert
        Assert.That(result, Is.EqualTo(expected));
    }
    
    [Test]
    public async Task UpdateAsync_ShouldUpdateEntity()
    {
        // Arrange
        var entity = new News { title = "Title", content = "Content"};
        var newsRep = new NewsRepository();
        entity = await newsRep.AddAsync(entity);
        entity.title = "Updated Title";
        // Act
        var result = await newsRep.UpdateAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result.title, Is.EqualTo("Updated Title"));
    }

    [Test]
    public async Task DeleteAsync_ShouldRemoveEntity()
    {
        var entity = new News { title = "Title", content = "Content"};
        // Arrange
        var newsRep = new NewsRepository();
        await newsRep.AddAsync(entity);
        // Act
        Task task =  newsRep.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
    
    [Test]
    public async Task DeleteAsync_NoEntities()
    {
        var newsRep = new NewsRepository();
        Task task =  newsRep.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
}