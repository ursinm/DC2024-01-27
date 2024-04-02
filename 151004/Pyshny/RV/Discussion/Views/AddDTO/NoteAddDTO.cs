using System.ComponentModel.DataAnnotations;

namespace Discussion.Views.DTO
{
    public class NoteAddDTO
    {
        [Required]
        public int newsId { get; set; }
        [Required]
        public string content { get; set; }
    }
}
