using Api.DTO.RequestDTO;
using Api.DTO.ResponseDTO;

namespace Api.Services.Interfaces
{
    public interface IStickerService
    {
        Task<IEnumerable<StickerResponseDto>> GetStickersAsync();

        Task<StickerResponseDto> GetStickerByIdAsync(long id);

        Task<StickerResponseDto> CreateStickerAsync(StickerRequestDto sticker);

        Task<StickerResponseDto> UpdateStickerAsync(StickerRequestDto sticker);

        Task DeleteStickerAsync(long id);
    }
}
