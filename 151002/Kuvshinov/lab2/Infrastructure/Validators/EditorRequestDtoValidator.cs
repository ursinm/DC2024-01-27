﻿using DC.DTO.RequestDTO;
using FluentValidation;

namespace DC.Infrastructure.Validators
{
	public class EditorRequestDtoValidator : AbstractValidator<EditorRequestDto>
	{
		public EditorRequestDtoValidator()
		{
			RuleFor(dto => dto.Login).Length(2, 64);
			RuleFor(dto => dto.Password).Length(8, 128);
			RuleFor(dto => dto.Firstname).Length(2, 64);
			RuleFor(dto => dto.Lastname).Length(2, 64);
		}
	}
}
