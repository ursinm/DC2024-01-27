using System.Numerics;
using REST.Models.Entity;
using REST.Repositories.interfaces;
using REST.Repositories.sample;

namespace REST.Repositories;

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