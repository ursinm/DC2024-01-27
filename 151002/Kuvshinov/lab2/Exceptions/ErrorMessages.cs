using Microsoft.AspNetCore.Http.HttpResults;
using DC.Models;

namespace DC.Exceptions
{
	public static class ErrorMessages
	{
		public static string EditorNotFoundMessage(long id) => $"Editor with id {id} was not found.";
		public static string StoryNotFoundMessage(long id) => $"Story with id {id} was not found.";
		public static string LabelNotFoundMessage(long id) => $"Label with id {id} was not found.";
		public static string NoteNotFoundMessage(long id) => $"Note with id {id} was not found.";
       
        public const string CreatorAlreadyExistsMessage = "Creator with this login already exists.";

        public const string IssueAlreadyExistsMessage = "Issue with this title already exists.";

        public const string LabelAlreadyExistsMessage = "This label already exists.";

        public static string AlreadyExistsMessage(Type entityType)
        {
            if (entityType == typeof(Editor))
            {
                return CreatorAlreadyExistsMessage;
            }
            if (entityType == typeof(Story))
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
