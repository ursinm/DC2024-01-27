using REST.Models.Entity;
using REST.Repositories.InMemory.sample;
using REST.Repositories.interfaces;

namespace REST.Repositories.InMemory;

public class LabelRepository : BaseRepository<Label>, ILabelRepository
{
    public Task<Label> AddAsync(Label entity)
    {
        var id = GlobalId++;
        entity.id = id;
        _entities.Add(id,entity);
        return Task.FromResult(entity);
    }

    public Task<Label> UpdateAsync(Label entity)
    {
        var id = entity.id;
        _entities[id] = entity;
        return Task.FromResult(_entities[id]);
    }
}