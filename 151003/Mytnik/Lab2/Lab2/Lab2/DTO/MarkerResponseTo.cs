using Lab2.DTO.Interface;

namespace Lab2.DTO
{
    public class MarkerResponseTo(int Id, string? Name) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
