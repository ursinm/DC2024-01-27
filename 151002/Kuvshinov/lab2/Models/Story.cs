namespace DC.Models
{
	public class Story : BaseModel
    {
		public long EditorId { get; set; }

		public Editor Editor { get; set; } = null!;

        public string Title { get; set; } = string.Empty;

        public string Content { get; set; } = string.Empty;

        public DateTime Created { get; set; }

		public DateTime Modified { get; set; }

        public ICollection<Label> Labels { get; set; } = Array.Empty<Label>();
    }
}
