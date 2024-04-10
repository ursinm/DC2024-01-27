using lab2.DTO.Interface;

namespace lab2.DTO
{
    public class LabelResponseTo(int Id, string? Name) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
