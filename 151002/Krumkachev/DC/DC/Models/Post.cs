namespace DC.Models
{
	public class Post
	{
		public long Id { get; set; }

		public long IssueId { get; set; }

		public Issue Issue { get; set; }

		public string Content { get; set; }
	}
}
