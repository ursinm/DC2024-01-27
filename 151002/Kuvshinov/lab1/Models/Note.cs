namespace DC.Models
{
	public class Note
	{
		public long Id { get; set; }

		public long StoryId { get; set; }

		public Story Story { get; set; }

		public string Content { get; set; }
	}
}
