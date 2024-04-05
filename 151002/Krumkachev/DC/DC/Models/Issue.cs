namespace DC.Models
{
	public class Issue : BaseModel
	{
		public long CreatorId { get; set; }

		public Creator Creator { get; set; } = null!;

		public string Title { get; set; } = string.Empty;

		public string Content { get; set; } = string.Empty;

		public DateTime Created { get; set; }

		public DateTime Modified { get; set; }

		public ICollection<Label> Labels { get; set; } = [];
	}
}
