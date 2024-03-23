using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
{
	public interface IIssueService
	{
		Task<IEnumerable<IssueResponseDto>> GetIssuesAsync();

		Task<IssueResponseDto> GetIssueByIdAsync(long id);

		Task<IssueResponseDto> CreateIssueAsync(IssueRequestDto issue);

		Task<IssueResponseDto> UpdateIssueAsync(IssueRequestDto issue);

		Task DeleteIssueAsync(long id);
	}
}
