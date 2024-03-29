using Lab2.DTO.Interface;
using Lab2.Models;

namespace Lab2.DTO
{
    public class CreatorResponseTo(int Id, string? Login, string? Password, string? Firstname, string? Lastname, ICollection<Tweet> Tweets) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public string? Login { get; set; } = Login;

        public string? Password { get; set; } =    Password;

        public string? Firstname { get; set; } = Firstname;

        public string? Lastname { get; set; } = Lastname;

        public virtual ICollection<Tweet> Tweets { get; set; } = Tweets;
    }
}
