using DC.DTO.RequestDTO;
using FluentValidation;

namespace DC.Infrastructure.Validators
{
	public class IssueRequestDtoValidator : AbstractValidator<IssueRequestDto>
	{
		public IssueRequestDtoValidator()
		{
			RuleFor(dto => dto.Title).Length(2, 64);
			RuleFor(dto => dto.Content).Length(4, 2048);
		}
	}
}
