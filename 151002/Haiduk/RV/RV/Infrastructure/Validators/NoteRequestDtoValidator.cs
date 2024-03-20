using Api.DTO.RequestDTO;
using FluentValidation;

namespace Api.Infrastructure.Validators
{
    public class NoteRequestDtoValidator : AbstractValidator<NoteRequestDto>
    {
        public NoteRequestDtoValidator()
        {
            RuleFor(dto => dto.Content).Length(2, 2048);
        }
    }
}
