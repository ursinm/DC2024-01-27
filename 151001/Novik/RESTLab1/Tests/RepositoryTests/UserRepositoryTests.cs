using REST.Models.Entity;
using REST.Repositories;

namespace Tests.RepositoryTests;

public class UserRepositoryTests
{
    [Test]
    public async Task AddAsync_ShouldAddEntity()
    {
        // Arrange
        var entity = new User { login = "login", password = "password", firstname = "firstname", lastname = "lastname"};
        var userRep = new UserRepository();
        // Act
        var result = await userRep.AddAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entity));
    }
    [Test]
    public async Task GetByIdAsync_ShouldReturnEntity()
    {
        // Arrange
        var entities = new List<User> { new User { login = "login", password = "password", firstname = "firstname", lastname = "lastname"} };
        var userRep = new UserRepository();
        await userRep.AddAsync(entities[0]);

        // Act
        var result = await userRep.GetByIdAsync(1);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entities[0]));
    }
    
    [Test]
    public async Task GetByIdAsync_EntityNotExists()
    {
        // Arrange
        var userRep = new UserRepository();

        // Act
        var result = await userRep.GetByIdAsync(1);

        // Assert
        Assert.That(result, Is.EqualTo(null));
    }

    [Test]
    public async Task GetAllAsync_ShouldReturnAllEntities()
    {
        // Arrange
        var entities = new List<User>
        {
            new User { login = "login", password = "password", firstname = "firstname", lastname = "lastname"},
            new User { login = "login", password = "password", firstname = "firstname", lastname = "lastname" }
        };
        var userRep = new UserRepository();
        foreach (var entity in entities)
        {
            await userRep.AddAsync(entity);
        }

        // Act
        var result = await userRep.GetAllAsync();

        // Assert
        Assert.NotNull(result);
        Assert.That(result.Count(), Is.EqualTo(2));
    }
    
    [Test]
    public async Task GetAllAsync_NoEntities()
    {
        // Arrange
        var userRep = new UserRepository();
        // Act
        var result = await userRep.GetAllAsync();
        var expected = new Dictionary<long, User>();
        // Assert
        Assert.That(result, Is.EqualTo(expected));
    }
    
    [Test]
    public async Task UpdateAsync_ShouldUpdateEntity()
    {
        // Arrange
        var entity = new User { login = "login", password = "password", firstname = "firstname", lastname = "lastname"};
        var userRep = new UserRepository();
        entity = await userRep.AddAsync(entity);
        entity.login = "Updated Login";
        // Act
        var result = await userRep.UpdateAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result.login, Is.EqualTo("Updated Login"));
    }

    [Test]
    public async Task DeleteAsync_ShouldRemoveEntity()
    {
        var entity = new User { login = "login", password = "password", firstname = "firstname", lastname = "lastname"};
        // Arrange
        var userRep = new UserRepository();
        await userRep.AddAsync(entity);
        // Act
        Task task =  userRep.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
    
    [Test]
    public async Task DeleteAsync_NoEntities()
    {
        var userRep = new UserRepository();
        Task task =  userRep.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
}