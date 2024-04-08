namespace DC.Models
{
	public class Label : BaseModel
    {
		public string Name { get; set; } = string.Empty;

        public List<Story> Storys { get; set; } = new();
    }
}
