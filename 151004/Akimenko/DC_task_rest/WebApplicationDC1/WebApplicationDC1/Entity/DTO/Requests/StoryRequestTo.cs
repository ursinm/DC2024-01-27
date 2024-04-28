namespace WebApplicationDC1.Entity.DTO.Requests
{
    public record class StoryRequestTO(int StoryId, string Title, string Content, DateTime Created, DateTime Modified);

}
