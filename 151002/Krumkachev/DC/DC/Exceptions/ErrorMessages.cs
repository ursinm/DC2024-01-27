namespace DC.Exceptions
{
	public static class ErrorMessages
	{
		public static string CreatorNotFoundMessage(long id) => $"Creator with id {id} was not found.";
		public static string IssueNotFoundMessage(long id) => $"Issue with id {id} was not found.";
		public static string LabelNotFoundMessage(long id) => $"Label with id {id} was not found.";
		public static string PostNotFoundMessage(long id) => $"Post with id {id} was not found.";

	}
}
