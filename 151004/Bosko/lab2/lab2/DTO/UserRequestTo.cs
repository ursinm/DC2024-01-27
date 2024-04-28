using lab2.DTO.Interface;

namespace lab2.DTO
{
    public class UserRequestTo(int Id, string? Login, string? Password, string? Firstname, string? Lastname) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public string? Login { get; set; } = Login;

        public string? Password { get; set; } = Password;

        public string? Firstname { get; set; } = Firstname;

        public string? Lastname { get; set; } = Lastname;
    }
}
