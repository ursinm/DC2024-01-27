using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace RV.Views.DTO
{
    [Serializable]
    public class NoteAddDTO
    {
        public int id { get; set; }

        [Required]
        public int newsId { get; set; }
        [Required]
        public string content { get; set; }
    }
}
