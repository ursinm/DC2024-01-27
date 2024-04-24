using lab2.DTO.Interface;

namespace lab2.DTO
{
    public class LabelRequestTo(int Id, string? Name) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
