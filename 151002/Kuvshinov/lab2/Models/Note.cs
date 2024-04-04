namespace DC.Models
{
	public class Note : BaseModel
    {
		public long StoryId { get; set; }

		public Story Story { get; set; } = null!;

		public string Content { get; set; } = string.Empty;
    }
}
