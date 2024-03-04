using REST.Models.Entity;
using REST.Repositories.interfaces;
using REST.Repositories.sample;

namespace REST.Repositories;

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