using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace RV.Models
{
    public class NewsSticker
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public int id { get; set; }

        [ForeignKey("News")]
        public int newsId { get; set; }
        public News? News { get; set; }

        [ForeignKey("Sticker")]
        public int stickerId { get; set; }
        public Sticker? Sticker { get; set; }
    }
}
