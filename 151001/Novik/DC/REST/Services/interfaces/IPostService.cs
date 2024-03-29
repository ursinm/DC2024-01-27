using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;

namespace REST.Services.interfaces;

public interface IPostService : IService<PostResponseTo, PostRequestTo>
{
    Task<IEnumerable<PostResponseTo>> GetAllAsync();
    
    Task<PostResponseTo?>? GetByIdAsync(long id);
    
    Task<PostResponseTo> AddAsync(PostRequestTo tEntityReq);
    
    Task<PostResponseTo> UpdateAsync(PostRequestTo tEntityReq);
    
    Task DeleteAsync(long id);
}