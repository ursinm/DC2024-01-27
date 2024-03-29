using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;

namespace REST.Services.interfaces;

public interface INewsService : IService<NewsResponseTo, NewsRequestTo>
{
    Task<IEnumerable<NewsResponseTo>> GetAllAsync();
    
    Task<NewsResponseTo?>? GetByIdAsync(long id);
    
    Task<NewsResponseTo> AddAsync(NewsRequestTo tEntityReq);
    
    Task<NewsResponseTo> UpdateAsync(NewsRequestTo tEntityReq);
    
    Task DeleteAsync(long id);
}