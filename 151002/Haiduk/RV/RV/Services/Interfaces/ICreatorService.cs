using Api.DTO.RequestDTO;
using Api.DTO.ResponseDTO;

namespace Api.Services.Interfaces
{
    public interface ICreatorService
    {
        Task<IEnumerable<CreatorResponseDto>> GetCreatorsAsync();

        Task<CreatorResponseDto> GetCreatorByIdAsync(long id);

        Task<CreatorResponseDto> CreateCreatorAsync(CreatorRequestDto creator);

        Task<CreatorResponseDto> UpdateCreatorAsync(CreatorRequestDto creator);

        Task DeleteCreatorAsync(long id);
    }
}
