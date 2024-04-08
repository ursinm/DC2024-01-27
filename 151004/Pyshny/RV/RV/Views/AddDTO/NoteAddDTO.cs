using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace RV.Views.DTO
{
    [Serializable]
    public class NoteAddDTO
    {
        [Required]
        public int newsId { get; set; }
        [Required]
        public string content { get; set; }
    }
}
