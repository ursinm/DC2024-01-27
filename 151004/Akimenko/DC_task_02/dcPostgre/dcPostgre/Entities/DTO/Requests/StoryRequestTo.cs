using System.ComponentModel.DataAnnotations;

namespace WebApplicationDC1.Entity.DTO.Requests
{
    public record class StoryRequestTO(int Id, int CreatorId, string Title, string Content, DateTime Created, DateTime Modified);
}
