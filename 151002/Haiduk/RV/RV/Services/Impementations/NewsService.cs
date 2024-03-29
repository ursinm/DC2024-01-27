using Api.DTO.RequestDTO;
using Api.DTO.ResponseDTO;
using Api.Exceptions;
using Api.Infrastructure.Validators;
using Api.Models;
using Api.Repositories.Interfaces;
using Api.Services.Interfaces;
using AutoMapper;
using FluentValidation;

namespace Api.Services.Impementations
{
    public class NewsService(INewsRepository newsRepository,
        IMapper mapper, NewsRequestDtoValidator validator) : INewsService
    {
        private readonly INewsRepository _newsRepository = newsRepository;
        private readonly IMapper _mapper = mapper;
        private readonly NewsRequestDtoValidator _validator = validator;

        public async Task<IEnumerable<NewsResponseDto>> GetNewsAsync()
        {
            var issues = await _newsRepository.GetAllAsync();
            return _mapper.Map<IEnumerable<NewsResponseDto>>(issues);
        }

        public async Task<NewsResponseDto> GetNewsByIdAsync(long id)
        {
            var issue = await _newsRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.NewsNotFoundMessage(id));
            return _mapper.Map<NewsResponseDto>(issue);
        }

        public async Task<NewsResponseDto> CreateNewsAsync(NewsRequestDto news)
        {
            _validator.ValidateAndThrow(news);
            var newsToCreate = _mapper.Map<News>(news);
            var createdIssue = await _newsRepository.CreateAsync(newsToCreate);
            return _mapper.Map<NewsResponseDto>(createdIssue);
        }

        public async Task<NewsResponseDto> UpdateNewsAsync(NewsRequestDto news)
        {
            _validator.ValidateAndThrow(news);
            var newsToUpdate = _mapper.Map<News>(news);
            var updatedNews = await _newsRepository.UpdateAsync(newsToUpdate)
                ?? throw new NotFoundException(ErrorMessages.NewsNotFoundMessage(news.Id));
            return _mapper.Map<NewsResponseDto>(updatedNews);
        }

        public async Task DeleteNewsAsync(long id)
        {
            if (!await _newsRepository.DeleteAsync(id))
            {
                throw new NotFoundException(ErrorMessages.NewsNotFoundMessage(id));
            }
        }
    }
}
