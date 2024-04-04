using DC.DTO.RequestDTO;
using FluentValidation;

namespace DC.Infrastructure.Validators
{
	public class LabelRequestDtoValidator : AbstractValidator<LabelRequestDto>
	{
		public LabelRequestDtoValidator()
		{
			RuleFor(dto => dto.Name).Length(2, 32);
		}
	}
}
