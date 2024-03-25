using REST.Models.Entity;
using REST.Repositories.InMemory.sample;
using REST.Repositories.interfaces;

namespace REST.Repositories.InMemory;

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