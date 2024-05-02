using Publisher.Entity.Common;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Entity.Db
{
    public class sticker(string name) : AbstractEntity
    {
        [MinLength(2)]
        public string Name { get; set; } = name;
        public ICollection<story> storys { get; set; } = [];

        public sticker() : this(string.Empty) { }
        public sticker(int id, string name) : this(name)
        {
            Id = id;
        }
    }
}
