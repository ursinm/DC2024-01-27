using REST.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace REST.Entity.Db
{
    public class Issue(string title, string content, DateTime created, DateTime modified) : AbstractEntity
    {
        [MinLength(2)]
        public string Title { get; set; } = title;
        [MinLength(4)]
        public string Content { get; set; } = content;
        public DateTime Created { get; set; } = created;
        public DateTime Modified { get; set; } = modified;
        public ICollection<Comment> Comments { get; set; } = [];
        public ICollection<Marker> Markers { get; set; } = [];
        public Creator Creator { get; set; } = null!;

        public Issue() : this(string.Empty, string.Empty, DateTime.Now, DateTime.Now) { }
        public Issue(int id, Creator creator, string title, string content, DateTime created, DateTime modified)
            : this(title, content, created, modified)
        {
            Id = id;
            Creator = creator;
        }
    }
}
