using System.ComponentModel.DataAnnotations;

namespace RV.Views.DTO
{
    public class NoteDTO
    {
        [Required]
        public int id { get; set; }
        [Required]
        public int newsId { get; set; }
        [Required]
        public string content { get; set; }
    }
}
