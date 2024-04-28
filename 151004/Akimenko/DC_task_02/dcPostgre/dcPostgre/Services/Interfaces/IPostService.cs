using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;

namespace WebApplicationDC1.Services.Interfaces
{
    public interface IPostService : ICRUDService<Post, PostRequestTO, PostResponseTO>
    {
        Task<IList<Post>> GetByStoryID(int storyId);
    }
}
