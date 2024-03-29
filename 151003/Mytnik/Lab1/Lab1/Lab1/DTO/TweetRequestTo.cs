using Lab1.DTO.Interface;
using Lab1.Models;

namespace Lab1.DTO
{
    public class TweetRequestTo(int Id, int? CreatorId, string? Title, string? Content) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public int? CreatorId { get; set; } = CreatorId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;


    }
}
