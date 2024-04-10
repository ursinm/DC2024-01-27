namespace lab2.Models
{
    public class Label
    {
        public int Id { get; set; }

        public string? Name { get; set; }

        public virtual ICollection<NewsLabel> NewsLabels { get; set; } = new List<NewsLabel>();
    }
}
