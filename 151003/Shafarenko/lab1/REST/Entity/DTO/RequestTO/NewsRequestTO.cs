namespace REST.Entity.DTO.RequestTO
{
    public record class NewsRequestTO(int CreatorId, string Title, string Content, DateTime Created, DateTime Modified);
}
