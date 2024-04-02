using REST.Models.Entity;
using REST.Repositories.InMemory.sample;
using REST.Repositories.interfaces;

namespace REST.Repositories.InMemory;

public class NewsRepository : BaseRepository<News>, INewsRepository
{
    public Task<News> AddAsync(News entity)
    {
        var id = GlobalId++;
        entity.id = id;
        _entities.Add(id,entity);
        return Task.FromResult(entity);
    }

    public Task<News> UpdateAsync(News entity)
    {
        var id = entity.id;
        _entities[id] = entity;
        return Task.FromResult(_entities[id]);
    }
}