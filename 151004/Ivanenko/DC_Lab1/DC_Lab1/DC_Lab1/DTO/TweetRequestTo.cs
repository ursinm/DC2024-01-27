using DC_Lab1.DTO.Interface;
using DC_Lab1.Models;

namespace DC_Lab1.DTO
{
    public class TweetRequestTo( int? EditorId, string? Title, string? Content) : IRequestTo
    {
        public int Id { get; set; } = 0;

        public int? EditorId { get; set; } = EditorId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;


    }
}
