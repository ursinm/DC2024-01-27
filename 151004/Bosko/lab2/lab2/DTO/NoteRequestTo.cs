using lab2.DTO.Interface;

namespace lab2.DTO
{
    public class NoteRequestTo(int Id, int? NewsId, string? Content) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public int? NewsId { get; set; } = NewsId;

        public string? Content { get; set; } = Content;
    }
}
