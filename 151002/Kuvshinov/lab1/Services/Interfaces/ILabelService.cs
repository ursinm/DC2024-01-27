using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
{
	public interface ILabelService
	{
		Task<IEnumerable<LabelResponseDto>> GetLabelsAsync();

		Task<LabelResponseDto> GetLabelByIdAsync(long id);

		Task<LabelResponseDto> CreateLabelAsync(LabelRequestDto label);

		Task<LabelResponseDto> UpdateLabelAsync(LabelRequestDto label);

		Task DeleteLabelAsync(long id);
	}
}
