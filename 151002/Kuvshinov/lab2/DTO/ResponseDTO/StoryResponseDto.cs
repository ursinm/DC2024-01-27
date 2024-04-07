using DC.Models;

namespace DC.DTO.ResponseDTO
{
	public class StoryResponseDto
	{
		public long Id { get; set; }

		public long EditorId { get; set; }

		public Editor Editor { get; set; }

		public string Title { get; set; }

		public string Content { get; set; }

		public DateTime Created { get; set; }

		public DateTime Modified { get; set; }
	}
}
