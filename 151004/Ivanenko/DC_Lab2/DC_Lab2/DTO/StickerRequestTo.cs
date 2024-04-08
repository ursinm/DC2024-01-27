using DC_Lab1.DTO.Interface;

namespace DC_Lab1.DTO
{
    public class StickerRequestTo(int? Id, string? Name) : IRequestTo
    {
        public int? Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
