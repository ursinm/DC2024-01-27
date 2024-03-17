namespace Api.Models
{
    public class Note
    {
        public long Id { get; set; }

        public long NewsId { get; set; }

        public News News { get; set; }

        public string Content { get; set; }
    }
}
