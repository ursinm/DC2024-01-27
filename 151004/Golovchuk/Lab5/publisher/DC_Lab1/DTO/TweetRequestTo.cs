using DC_Lab1.DTO.Interface;
using DC_Lab1.Models;

namespace DC_Lab1.DTO
{
    public class TweetRequestTo(int Id, int? creatorId, string? Title, string? Content) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public int? creatorId { get; set; } = creatorId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;


    }
}
