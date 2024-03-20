using REST.Models.Entity;
using REST.Repositories.InMemory.sample;
using REST.Repositories.interfaces;

namespace REST.Repositories.InMemory;

public class UserRepository : BaseRepository<User>, IUserRepository
{
    public Task<User> AddAsync(User entity)
    {
        var id = GlobalId++;
        entity.id = id;
        _entities.Add(id,entity);
        return Task.FromResult(entity);
    }

    public Task<User> UpdateAsync(User entity)
    {
        var id = entity.id;
        _entities[id] = entity;
        return Task.FromResult(_entities[id]);
    }
}