using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Numerics;

namespace REST.Models.Entity;

public class User
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long id { get; set; }
    
    [Required]
    [StringLength(64, MinimumLength = 2)] 
    public String login { get; set; }
    
    [Required]
    [StringLength(128, MinimumLength = 8)]
    public String password { get; set; }
    
    [Required]
    [StringLength(64, MinimumLength = 2)]
    public String firstname { get; set; }
    
    [Required]
    [StringLength(64, MinimumLength = 2)]
    public String lastname { get; set; }
    
    public ICollection<News> NewsList { get; set; } = new List<News>();
}