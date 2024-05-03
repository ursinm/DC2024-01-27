namespace Publisher.Entity.DTO.RequestTO
{
    public record class storyRequestTO(int Id, int creatorId, string Title, string Content, DateTime Created, DateTime Modified);
}
