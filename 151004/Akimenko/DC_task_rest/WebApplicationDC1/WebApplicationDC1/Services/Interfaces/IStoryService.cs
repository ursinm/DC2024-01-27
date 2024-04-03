using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;

namespace WebApplicationDC1.Services.Interfaces
{
    public interface IStoryService : ICRUDService<Story, StoryRequestTO, StoryResponseTO>
    {
        Task<StoryResponseTO> GetStoryByParam(IList<string> stickerNames, IList<int> stickersIds, string creatorLogin, string title, string content);
    }
}
