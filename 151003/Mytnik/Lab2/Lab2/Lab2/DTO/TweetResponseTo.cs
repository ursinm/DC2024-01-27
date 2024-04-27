using Lab2.DTO.Interface;
using Lab2.Models;

namespace Lab2.DTO
{
    public class TweetResponseTo(int Id, int? CreatorId, string? Title, string? Content, string? Created, string? Modified ,ICollection<Note> Notes, Creator? Creator) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public int? CreatorId { get; set; } = CreatorId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;

        public string? Created { get; set; } = Created;

        public string? Modified { get; set; } = Modified;

        public virtual ICollection<Note> Notes { get; set; } = Notes;

        public virtual Creator? Creator { get; set; } = Creator;
    }
}
