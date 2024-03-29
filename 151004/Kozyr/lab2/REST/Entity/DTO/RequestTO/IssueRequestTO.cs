namespace REST.Entity.DTO.RequestTO
{
    public record class IssueRequestTO(int CreatorId, string Title, string Content, DateTime Created, DateTime Modified);
}
