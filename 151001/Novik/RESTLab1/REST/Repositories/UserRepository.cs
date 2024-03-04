using System.Numerics;
using REST.Models.Entity;
using REST.Repositories.interfaces;
using REST.Repositories.sample;

namespace REST.Repositories;

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