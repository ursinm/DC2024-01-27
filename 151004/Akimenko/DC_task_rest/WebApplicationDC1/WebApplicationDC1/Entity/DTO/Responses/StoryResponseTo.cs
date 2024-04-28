namespace WebApplicationDC1.Entity.DTO.Responses
{
    public record class StoryResponseTO(int Id, int CreatorId, string Title, string Content, DateTime Created, DateTime Modified);
}
