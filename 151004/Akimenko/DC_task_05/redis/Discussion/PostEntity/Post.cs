using Discussion.Common;
using System.ComponentModel.DataAnnotations;

namespace Discussion.PostEntity
{
    public class Post(string content, string country) : AbstractEntity
    {
        public string Country { get; set; } = country;
        public int StoryId { get; set; }
        public string Content { get; set; } = content;

        public Post() : this(string.Empty, string.Empty) { }

        public Post(int id, int storyId, string content) : this(content, string.Empty)
        {
            Id = id;
            StoryId = storyId;
        }

        public Post(int id, int storyId, string content, string country) : this(content, country)
        {
            Id = id;
            StoryId = storyId;
        }
    }
}
