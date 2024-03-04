using System.ComponentModel.DataAnnotations;
using System.Numerics;

namespace REST.Models.DTOs.RequestIn;

public class LabelRequestTo
{
    public long id { get; set; }
    
    public long? newsId { get; set; }
    
    [StringLength(32, MinimumLength = 2)]
    public String? name { get; set; } = String.Empty;

}