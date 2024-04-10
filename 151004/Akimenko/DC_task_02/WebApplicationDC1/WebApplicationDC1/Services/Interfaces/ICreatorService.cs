using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;

namespace WebApplicationDC1.Services.Interfaces
{
    public interface ICreatorService : ICRUDService<Creator, CreatorRequestTO, CreatorResponseTO>
    {
        Task<CreatorResponseTO> GetByStoryID(int storyId);
    }
}
