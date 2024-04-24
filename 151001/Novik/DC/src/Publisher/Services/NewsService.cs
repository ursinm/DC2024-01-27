using AutoMapper;
using Publisher.Models.DTOs.Requests;
using Publisher.Models.DTOs.Responses;
using Publisher.Models.Entity;
using Publisher.Repositories.interfaces;
using Publisher.Services.interfaces;

namespace Publisher.Services;

public class NewsService : INewsService
{
    private readonly INewsRepository _newsRepository;
    private readonly IMapper _newsMapper;

    public NewsService(INewsRepository newsRepository,IMapper newsMapper)
    {
        _newsMapper = newsMapper;
        _newsRepository = newsRepository;
    }
    
    public async Task<IEnumerable<NewsResponseTo>> GetAllAsync()
    {
        var newsEntities = await _newsRepository.GetAllAsync();
        return _newsMapper.Map<IEnumerable<NewsResponseTo>>(newsEntities);
    }

    public async Task<NewsResponseTo?>? GetByIdAsync(long id)
    {
        var newsEntity = await _newsRepository.GetByIdAsync(id);
        if (newsEntity == null)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        return _newsMapper.Map<NewsResponseTo>(newsEntity);
    }

    public async Task<NewsResponseTo> AddAsync(NewsRequestTo newsRequest)
    {
        var newsEntity = _newsMapper.Map<News>(newsRequest);
        newsEntity = await _newsRepository.AddAsync(newsEntity);
        return _newsMapper.Map<NewsResponseTo>(newsEntity);
    }

    public async Task<NewsResponseTo> UpdateAsync(NewsRequestTo newsRequest)
    {
        var existingNews = await _newsRepository.Exists(newsRequest.id);
        if (!existingNews)
        {
            throw new KeyNotFoundException($"User with ID {newsRequest.id} not found.");
        }

        var updatedNews = _newsMapper.Map<News>(newsRequest);
        return _newsMapper.Map<NewsResponseTo>(await _newsRepository.UpdateAsync(updatedNews));
    }

    public async Task DeleteAsync(long id)
    {
        var existingNews = await _newsRepository.Exists(id);
        if (!existingNews)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        await _newsRepository.DeleteAsync(id);
    }
    
}