using System.Numerics;

namespace REST.Models.DTOs.Responses;

public class LabelResponseTo
{
    public long id { get; set; }
    
    public long? newsId { get; set; }
    
    public String? name { get; set; } = String.Empty;

}