namespace DC.Models
{
	public class Editor : BaseModel
    {
		public string Login { get; set; } = string.Empty;

        public string Password { get; set; } = string.Empty;

        public string Firstname { get; set; } = string.Empty;

        public string Lastname { get; set; } = string.Empty;
    }
}
