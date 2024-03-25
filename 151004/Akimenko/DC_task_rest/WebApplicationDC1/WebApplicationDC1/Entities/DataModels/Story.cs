using System.ComponentModel.DataAnnotations;

namespace WebApplicationDC1.Entity.DataModel
{
    public class Story(string title, string content, DateTime created, DateTime modified) : Entity
    {
        [MinLength(2)]
        public string Title { get; set; } = title;
        [MinLength(4)]
        public string Content { get; set; } = content;
        public DateTime Created { get; set; } = created;
        public DateTime Modified { get; set; } = modified;
        public ICollection<Post> Posts { get; set; } = [];
        public ICollection<Sticker> Stickers { get; set; } = [];
        public Creator Creator { get; set; } = null!;

        public Story() : this(string.Empty, string.Empty, DateTime.Now, DateTime.Now) { }
        public Story(int id, Creator creator, string title, string content, DateTime created, DateTime modified)
            : this(title, content, created, modified)
        {
            Id = id;
            Creator = creator;
        }
    }
}
