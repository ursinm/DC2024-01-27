using Api.DTO.RequestDTO;
using FluentValidation;

namespace Api.Infrastructure.Validators
{
    public class StickerRequestDtoValidator : AbstractValidator<StickerRequestDto>
    {
        public StickerRequestDtoValidator()
        {
            RuleFor(dto => dto.Name).Length(2, 32);
        }
    }
}
