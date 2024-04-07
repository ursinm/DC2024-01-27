using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
{
	public interface IStoryService
	{
		Task<IEnumerable<StoryResponseDto>> GetStorysAsync();

		Task<StoryResponseDto> GetStoryByIdAsync(long id);

		Task<StoryResponseDto> CreateStoryAsync(StoryRequestDto story);

		Task<StoryResponseDto> UpdateStoryAsync(StoryRequestDto story);

		Task DeleteStoryAsync(long id);
	}
}
