using REST.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace REST.Entity.Db
{
    public class Comment(string content) : AbstractEntity
    {
        public int IssueId { get; set; }
        [MinLength(2)]
        public string Content { get; set; } = content;
        public Issue Issue { get; set; } = null!;

        public Comment() : this(string.Empty) { }
        public Comment(int id, int issueId, string content) : this(content)
        {
            Id = id;
            IssueId = issueId;
        }
    }
}
