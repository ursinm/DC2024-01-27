using Lab2.DTO.Interface;
using Lab2.Models;

namespace Lab2.DTO
{
    public class TweetRequestTo(int Id, int? CreatorId, string? Title, string? Content) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public int? CreatorId { get; set; } = CreatorId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;


    }
}
