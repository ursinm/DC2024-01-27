using System.ComponentModel.DataAnnotations;

namespace Discussion.Views.DTO
{
    public class NoteUpdateDTO
    {
        [Required]
        public int id { get; set; }
        public int? newsId { get; set; } = null;
        public string content { get; set; } = null;
    }
}
