using Lab2.DTO.Interface;

namespace Lab2.DTO
{
    public class NoteRequestTo(int Id,int? TweetId, string? Content) : IRequestTo
    {
        public int Id { get; set; } = Id;
        public int? TweetId { get; set; } = TweetId;

        public string? Content { get; set; } = Content;
    }
}
