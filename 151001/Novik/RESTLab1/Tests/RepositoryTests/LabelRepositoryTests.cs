using System.Collections;
using Microsoft.AspNetCore.Mvc;
using REST.Controllers;
using REST.Models.Entity;
using REST.Repositories;

namespace Tests.RepositoryTests;

public class LabelRepositoryTests
{

    [Test]
    public async Task AddAsync_ShouldAddEntity()
    {
        // Arrange
        var entity = new Label { id = 1, name = "Label 1" };
        var labelRepository = new LabelRepository();
        // Act
        var result = await labelRepository.AddAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entity));
    }
    [Test]
    public async Task GetByIdAsync_ShouldReturnEntity()
    {
        // Arrange
        var entities = new List<Label> { new Label { id = 1, name = "Label 1" } };
        var labelRep = new LabelRepository();
        await labelRep.AddAsync(entities[0]);

        // Act
        var result = await labelRep.GetByIdAsync(1);

        // Assert
        Assert.NotNull(result);
        Assert.That(result, Is.EqualTo(entities[0]));
    }
    
    [Test]
    public async Task GetByIdAsync_EntityNotExists()
    {
        // Arrange
        var labelRep = new LabelRepository();

        // Act
        var result = await labelRep.GetByIdAsync(1);

        // Assert
        Assert.That(result, Is.EqualTo(null));
    }

    [Test]
    public async Task GetAllAsync_ShouldReturnAllEntities()
    {
        // Arrange
        var entities = new List<Label>
        {
            new Label { id = 1, name = "Label 1" },
            new Label { id = 2, name = "Label 2" }
        };
        var labelRep = new LabelRepository();
        foreach (var entity in entities)
        {
            await labelRep.AddAsync(entity);
        }

        // Act
        var result = await labelRep.GetAllAsync();

        // Assert
        Assert.NotNull(result);
        Assert.That(result.Count(), Is.EqualTo(2));
    }
    
    [Test]
    public async Task GetAllAsync_NoEntities()
    {
        // Arrange
        var labelRep = new LabelRepository();
        // Act
        var result = await labelRep.GetAllAsync();
        var expected = new Dictionary<long, Label>();
        // Assert
        Assert.That(result, Is.EqualTo(expected));
    }
    
    [Test]
    public async Task UpdateAsync_ShouldUpdateEntity()
    {
        // Arrange
        var entity = new Label { id = 1, name = "Label 1" };
        var labelRepository = new LabelRepository();
        await labelRepository.AddAsync(entity);
        entity.name = "Updated Label";
        // Act
        var result = await labelRepository.UpdateAsync(entity);

        // Assert
        Assert.NotNull(result);
        Assert.That(result.name, Is.EqualTo("Updated Label"));
    }

    [Test]
    public async Task DeleteAsync_ShouldRemoveEntity()
    {
        var entity = new Label { id = 1, name = "Label 1" };
        // Arrange
        var labelRepository = new LabelRepository();
        await labelRepository.AddAsync(entity);
        // Act
        Task task =  labelRepository.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
    
    [Test]
    public async Task DeleteAsync_NoEntities()
    {
        var labelRepository = new LabelRepository();
        Task task =  labelRepository.DeleteAsync(1);

        // Assert
        // Verify that DeleteAsync method was called
        Assert.That(task,Is.EqualTo(Task.CompletedTask));
    }
}