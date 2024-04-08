using DC.Models;

namespace DC.Exceptions
{
	public static class ErrorMessages
	{
		public static string CreatorNotFoundMessage(long id) => $"Creator with id {id} was not found.";
		public static string IssueNotFoundMessage(long id) => $"Issue with id {id} was not found.";
		public static string LabelNotFoundMessage(long id) => $"Label with id {id} was not found.";
		public static string PostNotFoundMessage(long id) => $"Post with id {id} was not found.";

		public const string CreatorAlreadyExistsMessage = "Creator with this login already exists.";

		public const string IssueAlreadyExistsMessage = "Issue with this title already exists.";

		public const string LabelAlreadyExistsMessage = "This label already exists.";

		public static string AlreadyExistsMessage(Type entityType)
		{
			if (entityType == typeof(Creator))
			{
				return CreatorAlreadyExistsMessage;
			}
			if (entityType == typeof(Issue))
			{
				return IssueAlreadyExistsMessage;
			}
			if (entityType == typeof(Label))
			{
				return LabelAlreadyExistsMessage;
			}
			return "Such an object already exists.";
		}

	}
}
