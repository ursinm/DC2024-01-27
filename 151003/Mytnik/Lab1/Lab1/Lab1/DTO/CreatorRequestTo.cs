using Lab1.Models;
using Lab1.DTO.Interface;

namespace Lab1.DTO
{
    public class CreatorRequestTo(int Id,string? Login, string? Password, string? Firstname, string? Lastname) : IRequestTo
    {
        public int Id { get; set; } = Id;
        public string? Login { get; set; } = Login;

        public string? Password { get; set; } = Password;

        public string? Firstname { get; set; } = Firstname;

        public string? Lastname { get; set; } = Lastname;

      
    }
}
