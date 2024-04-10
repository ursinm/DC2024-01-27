namespace DC.Exceptions
{
	public static class ErrorMessages
	{
		public static string EditorNotFoundMessage(long id) => $"Editor with id {id} was not found.";
		public static string StoryNotFoundMessage(long id) => $"Story with id {id} was not found.";
		public static string LabelNotFoundMessage(long id) => $"Label with id {id} was not found.";
		public static string NoteNotFoundMessage(long id) => $"Note with id {id} was not found.";

	}
}
