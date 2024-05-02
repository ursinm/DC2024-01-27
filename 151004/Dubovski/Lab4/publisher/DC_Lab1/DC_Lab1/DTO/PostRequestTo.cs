using DC_Lab1.DTO.Interface;
using System.Text.Json.Serialization;

namespace DC_Lab1.DTO
{
    public class PostRequestTo : IRequestTo
    {
        public int Id { get; set; }

        public int? TweetId { get; set; }

        public string? Content { get; set; }
    }
}
