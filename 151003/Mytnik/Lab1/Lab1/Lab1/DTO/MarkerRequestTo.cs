using Lab1.DTO.Interface;

namespace Lab1.DTO
{
    public class MarkerRequestTo(int? Id, string? Name) : IRequestTo
    {
        public int? Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
