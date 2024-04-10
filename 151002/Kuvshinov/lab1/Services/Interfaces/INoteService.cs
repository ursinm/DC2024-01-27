using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;

namespace DC.Services.Interfaces
{
	public interface INoteService
    {
		Task<IEnumerable<NoteResponseDto>> GetNotesAsync();

		Task<NoteResponseDto> GetNoteByIdAsync(long id);

		Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto note);

		Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto note);

		Task DeleteNoteAsync(long id);
	}
}
