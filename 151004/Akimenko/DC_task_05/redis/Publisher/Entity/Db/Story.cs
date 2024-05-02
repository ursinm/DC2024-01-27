using Publisher.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Entity.Db
{
    public class story(string title, string content, DateTime created, DateTime modified) : AbstractEntity
    {
        [MinLength(2)]
        public string Title { get; set; } = title;
        [MinLength(4)]
        public string Content { get; set; } = content;
        public DateTime Created { get; set; } = created;
        public DateTime Modified { get; set; } = modified;
        public ICollection<sticker> stickers { get; set; } = [];
        public int creatorId { get; set; }
        public creator creator { get; set; } = null!;

        public story() : this(string.Empty, string.Empty, DateTime.Now, DateTime.Now) { }
        public story(int id, int creatorId, string title, string content, DateTime created, DateTime modified)
            : this(title, content, created, modified)
        {
            Id = id;
            this.creatorId = creatorId;
            creator = new() { Id = creatorId };
        }
    }
}
