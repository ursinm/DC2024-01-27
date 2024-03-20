using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;

namespace REST.Services.interfaces;

public interface IUserService : IService<UserResponseTo,UserRequestTo>
{
    Task<IEnumerable<UserResponseTo>> GetAllAsync();
    
    Task<UserResponseTo?>? GetByIdAsync(long id);
    
    Task<UserResponseTo> AddAsync(UserRequestTo tEntityReq);
    
    Task<UserResponseTo> UpdateAsync(UserRequestTo tEntityReq);
    
    Task DeleteAsync(long id);
}