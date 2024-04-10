using System.ComponentModel.DataAnnotations;

namespace REST.Models.DTOs.RequestIn;

public class NewsRequestTo
{
    public long id { get; set; }
    
    public long? userId { get; set; }
    
    [Required]
    [StringLength(64, MinimumLength = 2)]
    public String title { get; set; }
    
    [StringLength(2048, MinimumLength = 4)]
    public String? content { get; set; }
    
    public DateTime? created { get; set; }
    
    public DateTime? modified { get; set; }
    
}