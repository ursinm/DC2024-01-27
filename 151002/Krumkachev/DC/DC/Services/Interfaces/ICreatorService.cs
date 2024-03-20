using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
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
