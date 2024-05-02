using Publisher.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Entity.Db
{
    public class Post(string content) : AbstractEntity
    {
        public int NewsId { get; set; }
        [MinLength(2)]
        public string Content { get; set; } = content;
        public News News { get; set; } = null!;

        public Post() : this(string.Empty) { }
        public Post(int id, int newsId, string content, News news) : this(content)
        {
            Id = id;
            NewsId = newsId;
            News = news;
        }
    }
}
