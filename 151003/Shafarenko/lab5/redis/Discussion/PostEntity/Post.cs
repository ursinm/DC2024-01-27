using Discussion.Common;

namespace Discussion.PostEntity
{
    public class Post(string content, string country) : AbstractEntity
    {
        public string Country { get; set; } = country;
        public int NewsId { get; set; }
        public string Content { get; set; } = content;

        public Post() : this(string.Empty, string.Empty) { }

        public Post(int id, int newsId, string content) : this(content, string.Empty)
        {
            Id = id;
            NewsId = newsId;
        }

        public Post(int id, int newsId, string content, string country) : this(content, country)
        {
            Id = id;
            NewsId = newsId;
        }
    }
}
