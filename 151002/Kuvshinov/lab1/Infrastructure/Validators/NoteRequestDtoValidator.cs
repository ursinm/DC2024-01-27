using DC.DTO.RequestDTO;
using FluentValidation;

namespace DC.Infrastructure.Validators
{
	public class NoteRequestDtoValidator : AbstractValidator<NoteRequestDto>
	{
		public NoteRequestDtoValidator()
		{
			RuleFor(dto => dto.Content).Length(2, 2048);
		}
	}
}
