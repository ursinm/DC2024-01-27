namespace Publisher.Entity.DTO.ResponseTO
{
    public record class storyResponseTO(int Id, int creatorId, string Title, string Content, DateTime Created,
        DateTime Modified);
}
