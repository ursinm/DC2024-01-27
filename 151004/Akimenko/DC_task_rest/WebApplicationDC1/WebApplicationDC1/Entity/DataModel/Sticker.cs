using static System.Net.Mime.MediaTypeNames;
using System.ComponentModel.DataAnnotations;

namespace WebApplicationDC1.Entity.DataModel
{
    public class Sticker(string name) : Entity
    {
        [MinLength(2)]
        public string Name { get; set; } = name;
        public ICollection<Story> Stories { get; set; } = [];

        public Sticker() : this(string.Empty) { }
        public Sticker(int id, string name) : this(name)
        {
            Id = id;
        }
    }
}
