namespace lab2.Models
{
    public partial class NewsLabel
    {
        public int Id { get; set; }

        public int? NewsId { get; set; }

        public int? LabelId { get; set; }

        public News? News { get; set; }

        public Label Label { get; set; }
    }
}
