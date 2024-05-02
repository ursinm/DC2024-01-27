using AutoMapper;
using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;
using DC.Exceptions;
using DC.Infrastructure.Validators;
using DC.Models;
using DC.Repositories.Interfaces;
using DC.Services.Interfaces;
using FluentValidation;

namespace DC.Services.Impementations
{
	public class LabelService : ILabelService
	{


		public LabelService(ILabelRepository labelRepository,
			IMapper mapper, LabelRequestDtoValidator validator)
		{
			_labelRepository = labelRepository;
			_mapper = mapper;
			_validator = validator;
		}

	    private readonly ILabelRepository _labelRepository;
		private readonly IMapper _mapper;
		private readonly LabelRequestDtoValidator _validator;

		public async Task<IEnumerable<LabelResponseDto>> GetLabelsAsync()
		{
			var labels = await _labelRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<LabelResponseDto>>(labels);
		}

		public async Task<LabelResponseDto> GetLabelByIdAsync(long id)
		{
			var label = await _labelRepository.GetByIdAsync(id)
				?? throw new NotFoundException(ErrorMessages.LabelNotFoundMessage(id));
			return _mapper.Map<LabelResponseDto>(label);
		}

		public async Task<LabelResponseDto> CreateLabelAsync(LabelRequestDto label)
		{
			_validator.ValidateAndThrow(label);
			var labelToCreate = _mapper.Map<Label>(label);
			var createdLabel = await _labelRepository.CreateAsync(labelToCreate);
			return _mapper.Map<LabelResponseDto>(createdLabel);
		}

		public async Task<LabelResponseDto> UpdateLabelAsync(LabelRequestDto label)
		{
			_validator.ValidateAndThrow(label);
			var labelToUpdate = _mapper.Map<Label>(label);
			var updatedLabel = await _labelRepository.UpdateAsync(labelToUpdate)
				?? throw new NotFoundException(ErrorMessages.LabelNotFoundMessage(label.Id));
			return _mapper.Map<LabelResponseDto>(updatedLabel);
		}

		public async Task DeleteLabelAsync(long id)
		{
			if (!await _labelRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.LabelNotFoundMessage(id));
			}
		}
	}
}
