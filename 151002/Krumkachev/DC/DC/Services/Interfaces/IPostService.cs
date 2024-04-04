using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
{
	public interface IPostService
	{
		Task<IEnumerable<PostResponseDto>> GetPostsAsync();

		Task<PostResponseDto> GetPostByIdAsync(long id);

		Task<PostResponseDto> CreatePostAsync(PostRequestDto post);

		Task<PostResponseDto> UpdatePostAsync(PostRequestDto post);

		Task DeletePostAsync(long id);
	}
}
