using Lab1.DTO.Interface;

namespace Lab1.DTO
{
    public class MarkerResponseTo(int Id, string? Name) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
