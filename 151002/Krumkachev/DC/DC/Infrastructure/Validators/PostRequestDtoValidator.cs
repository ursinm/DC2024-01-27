using DC.DTO.RequestDTO;
using FluentValidation;

namespace DC.Infrastructure.Validators
{
	public class PostRequestDtoValidator : AbstractValidator<PostRequestDto>
	{
		public PostRequestDtoValidator()
		{
			RuleFor(dto => dto.Content).Length(2, 2048);
		}
	}
}
