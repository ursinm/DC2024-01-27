using DC.Models;

namespace DC.DTO.RequestDTO
{
	public class NoteRequestDto
	{
		public long Id { get; set; }

		public long StoryId { get; set; }

		public string Content { get; set; }
	}
}
