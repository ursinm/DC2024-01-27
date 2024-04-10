using lab2.DTO.Interface;
using lab2.Models;

namespace lab2.DTO
{
    public class NoteResponseTo(int Id, int? NewsId, string? Content, News? News) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public int? NewsId { get; set; } = NewsId;

        public string? Content { get; set; } = Content;

        public News? News { get; set; } = News;
    }
}
