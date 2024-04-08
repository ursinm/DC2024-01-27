using Lab2.DTO.Interface;

namespace Lab2.DTO
{
    public class MarkerRequestTo(int? Id, string? Name) : IRequestTo
    {
        public int? Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
