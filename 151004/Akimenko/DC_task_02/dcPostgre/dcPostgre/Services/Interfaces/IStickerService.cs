using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;

namespace WebApplicationDC1.Services.Interfaces
{
    public interface IStickerService : ICRUDService<Sticker, StickerRequestTO, StickerResponseTO>
    {
        Task<IList<StickerResponseTO>> GetByStoryID(int storyId);
    }
}
