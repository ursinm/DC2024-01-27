using System.Numerics;
using REST.Models.Entity;
using REST.Repositories.interfaces;
using REST.Repositories.sample;

namespace REST.Repositories;

public class PostRepository : BaseRepository<Post>, IPostRepository
{
    public Task<Post> AddAsync(Post entity)
    {
        var id = GlobalId++;
        entity.id = id;
        _entities.Add(id,entity);
        return Task.FromResult(entity);
    }

    public Task<Post> UpdateAsync(Post entity)
    {
        var id = entity.id;
        _entities[id] = entity;
        return Task.FromResult(_entities[id]);
    }
}