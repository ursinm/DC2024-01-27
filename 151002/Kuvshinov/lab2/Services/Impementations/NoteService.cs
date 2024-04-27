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
	public class NoteService : INoteService
    {

        private readonly INoteRepository _noteRepository;
        private readonly IMapper _mapper;
        private readonly NoteRequestDtoValidator _validator;

        public NoteService(INoteRepository noteRepository,
            IMapper mapper, NoteRequestDtoValidator validator)
		{
			_noteRepository = noteRepository;
			_mapper = mapper;
			_validator = validator;
		}

		public async Task<IEnumerable<NoteResponseDto>> GetNotesAsync()
		{
			var notes = await _noteRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<NoteResponseDto>>(notes);
		}

		public async Task<NoteResponseDto> GetNoteByIdAsync(long id)
		{
			var note = await _noteRepository.GetByIdAsync(id)
				?? throw new NotFoundException(ErrorMessages.NoteNotFoundMessage(id));
			return _mapper.Map<NoteResponseDto>(note);
		}

		public async Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto note)
		{
			_validator.ValidateAndThrow(note);
			var noteToCreate = _mapper.Map<Note>(note);
			var createdNote = await _noteRepository.CreateAsync(noteToCreate);
			return _mapper.Map<NoteResponseDto>(createdNote);
		}

		public async Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto note)
		{
			_validator.ValidateAndThrow(note);
			var noteToUpdate = _mapper.Map<Note>(note);
			var updatedNote = await _noteRepository.UpdateAsync(noteToUpdate)
				?? throw new NotFoundException(ErrorMessages.NoteNotFoundMessage(note.Id));
			return _mapper.Map<NoteResponseDto>(updatedNote);
		}

		public async Task DeleteNoteAsync(long id)
		{
			if (!await _noteRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.NoteNotFoundMessage(id));
			}
		}
		
	}
}
