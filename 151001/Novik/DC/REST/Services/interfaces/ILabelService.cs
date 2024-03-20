using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;

namespace REST.Services.interfaces;

public interface ILabelService : IService<LabelResponseTo, LabelRequestTo>
{
    Task<IEnumerable<LabelResponseTo>> GetAllAsync();
    
    Task<LabelResponseTo?>? GetByIdAsync(long id);
    
    Task<LabelResponseTo> AddAsync(LabelRequestTo tEntityReq);
    
    Task<LabelResponseTo> UpdateAsync(LabelRequestTo tEntityReq);
    
    Task DeleteAsync(long id);
}