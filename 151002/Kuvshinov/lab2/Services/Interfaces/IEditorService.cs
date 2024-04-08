using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
{
	public interface IEditorService
	{
		Task<IEnumerable<EditorResponseDto>> GetEditorsAsync();

		Task<EditorResponseDto> GetEditorByIdAsync(long id);

		Task<EditorResponseDto> CreateEditorAsync(EditorRequestDto editor);

		Task<EditorResponseDto> UpdateEditorAsync(EditorRequestDto editor);

		Task DeleteEditorAsync(long id);
	}
}
