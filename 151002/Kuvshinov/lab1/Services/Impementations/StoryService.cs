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
	public class StoryService : IStoryService
	{

		public StoryService(IStoryRepository storyRepository,
			IMapper mapper, StoryRequestDtoValidator validator)
		{
			_storyRepository = storyRepository;
			_mapper = mapper;
			_validator = validator;
		}

		private readonly IStoryRepository _storyRepository;
		private readonly IMapper _mapper;
		private readonly StoryRequestDtoValidator _validator;

		public async Task<IEnumerable<StoryResponseDto>> GetStorysAsync()
		{
			var storys = await _storyRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<StoryResponseDto>>(storys);
		}

		public async Task<StoryResponseDto> GetStoryByIdAsync(long id)
		{
			var story = await _storyRepository.GetByIdAsync(id)
				?? throw new NotFoundException(ErrorMessages.StoryNotFoundMessage(id));
			return _mapper.Map<StoryResponseDto>(story);
		}

		public async Task<StoryResponseDto> CreateStoryAsync(StoryRequestDto story)
		{
			_validator.ValidateAndThrow(story);
			var storyToCreate = _mapper.Map<Story>(story);
			var createdStory = await _storyRepository.CreateAsync(storyToCreate);
			return _mapper.Map<StoryResponseDto>(createdStory);
		}

		public async Task<StoryResponseDto> UpdateStoryAsync(StoryRequestDto story)
		{
			_validator.ValidateAndThrow(story);
			var storyToUpdate = _mapper.Map<Story>(story);
			var updatedStory = await _storyRepository.UpdateAsync(storyToUpdate)
				?? throw new NotFoundException(ErrorMessages.StoryNotFoundMessage(story.Id));
			return _mapper.Map<StoryResponseDto>(updatedStory);
		}

		public async Task DeleteStoryAsync(long id)
		{
			if (!await _storyRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.StoryNotFoundMessage(id));
			}
		}
	}
}
