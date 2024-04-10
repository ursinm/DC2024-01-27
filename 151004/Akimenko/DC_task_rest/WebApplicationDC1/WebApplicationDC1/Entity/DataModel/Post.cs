using static System.Net.Mime.MediaTypeNames;
using System.ComponentModel.DataAnnotations;

namespace WebApplicationDC1.Entity.DataModel
{
    public class Post(string content) : Entity
    {
        public int StoryId { get; set; }
        [MinLength(2)]
        public string Content { get; set; } = content;
        public Story Story { get; set; } = null!;

        public Post() : this(string.Empty) { }
        public Post(int id, int storyId, string content) : this(content)
        {
            Id = id;
            StoryId = storyId;
        }
    }
}
