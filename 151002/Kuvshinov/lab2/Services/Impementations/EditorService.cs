using AutoMapper;
using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;
using DC.Exceptions;
using DC.Infrastructure.Validators;
using DC.Models;
using DC.Repositories.Interfaces;
using DC.Services.Interfaces;
using FluentValidation;
using System.Numerics;

namespace DC.Services.Impementations
{
	public class EditorService : IEditorService
	{

		public EditorService(IEditorRepository editorRepository,
			IMapper mapper, EditorRequestDtoValidator validator)
		{
			_editorRepository = editorRepository;
			_mapper = mapper;
			_validator = validator;
		}

		private readonly IEditorRepository _editorRepository;
		private readonly IMapper _mapper;
		private readonly EditorRequestDtoValidator _validator;

		public async Task<IEnumerable<EditorResponseDto>> GetEditorsAsync()
		{
			var editors = await _editorRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<EditorResponseDto>>(editors);
		}

		public async Task<EditorResponseDto> GetEditorByIdAsync(long id)
		{
			var editor = await _editorRepository.GetByIdAsync(id) 
				?? throw new NotFoundException(ErrorMessages.EditorNotFoundMessage(id));
			return _mapper.Map<EditorResponseDto>(editor);
		}

		public async Task<EditorResponseDto> CreateEditorAsync(EditorRequestDto editor)
		{
			_validator.ValidateAndThrow(editor);
			var editorToCreate = _mapper.Map<Editor>(editor);
			var createdEditor = await _editorRepository.CreateAsync(editorToCreate);
			return _mapper.Map<EditorResponseDto>(createdEditor);
		}

		public async Task<EditorResponseDto> UpdateEditorAsync(EditorRequestDto editor)
		{
			_validator.ValidateAndThrow(editor);
			var editorToUpdate = _mapper.Map<Editor>(editor);
			var updatedEditor = await _editorRepository.UpdateAsync(editorToUpdate)
				?? throw new NotFoundException(ErrorMessages.EditorNotFoundMessage(editor.Id));
			return _mapper.Map<EditorResponseDto>(updatedEditor);
		}

		public async Task DeleteEditorAsync(long id)
		{
			if (!await _editorRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.EditorNotFoundMessage(id));
			}
		}
	}
}
