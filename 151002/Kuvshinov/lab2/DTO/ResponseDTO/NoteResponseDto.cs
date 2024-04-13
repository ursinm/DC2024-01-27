using DC.Models;

namespace DC.DTO.ResponseDTO
{
	public class NoteResponseDto
	{
		public long Id { get; set; }

		public long StoryId { get; set; }

		public Story Story { get; set; }

		public string Content { get; set; }
	}
}
