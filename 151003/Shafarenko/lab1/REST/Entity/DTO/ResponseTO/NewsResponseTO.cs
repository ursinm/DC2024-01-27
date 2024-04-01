namespace REST.Entity.DTO.ResponseTO
{
    public record class NewsResponseTO(int Id, int CreatorId, string Title, string Content, DateTime Created, 
        DateTime Modified);
}
