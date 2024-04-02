using Api.DTO.RequestDTO;
using Api.DTO.ResponseDTO;

namespace Api.Services.Interfaces
{
    public interface INewsService
    {
        Task<IEnumerable<NewsResponseDto>> GetNewsAsync();

        Task<NewsResponseDto> GetNewsByIdAsync(long id);

        Task<NewsResponseDto> CreateNewsAsync(NewsRequestDto issue);

        Task<NewsResponseDto> UpdateNewsAsync(NewsRequestDto issue);

        Task DeleteNewsAsync(long id);
    }
}
