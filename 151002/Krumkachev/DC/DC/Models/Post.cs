namespace DC.Models
{
	public class Post : BaseModel
	{
		public long IssueId { get; set; }

		public Issue Issue { get; set; } = null!;

		public string Content { get; set; } = string.Empty;
	}
}
