using DC_Lab1.DTO.Interface;

namespace DC_Lab1.DTO
{
    public class PostRequestTo(int? TweetId, string? Content) : IRequestTo
    {
        public int Id { get; set; } = 0;
        public int? TweetId { get; set; } = TweetId;

        public string? Content { get; set; } = Content;
    }
}
