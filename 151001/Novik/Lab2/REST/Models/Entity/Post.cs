using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Numerics;

namespace REST.Models.Entity;

public class Post
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long id { get; set; }
    
    [ForeignKey("newsId")]
    public long? newsId { get; set; }
    
    [StringLength(2048, MinimumLength = 2)]
    public String? content { get; set; }
    
    public News News { get; set; }
}