package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.NewsRepository;
import by.bsuir.publisher.model.entity.News;
import by.bsuir.publisher.model.request.NewsRequestTo;
import by.bsuir.publisher.model.response.NewsResponseTo;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.exceptions.ResourceStateException;
import by.bsuir.publisher.service.mapper.NewsMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@CacheConfig(cacheNames = "newsCache")
@RequiredArgsConstructor
public class NewsService implements RestService<NewsRequestTo, NewsResponseTo> {
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @Cacheable(cacheNames = "news")
    @Override
    public List<NewsResponseTo> findAll() {
        return newsMapper.getListResponseTo(newsRepository.findAll());
    }

    @Cacheable(value = "news", key = "#id", unless = "#result == null")
    @Override
    public NewsResponseTo findById(Long id) {
        return newsMapper.getResponseTo(newsRepository
                .findById(id)
                .orElseThrow(() -> newsNotFoundException(id)));
    }

    @CacheEvict(cacheNames = "news", allEntries = true)
    @Override
    public NewsResponseTo create(NewsRequestTo newsTo) {
        News news = newsMapper.getNews(newsTo);
        news.setCreated(new Date(System.currentTimeMillis()));
        news.setModified(news.getCreated());
        return newsMapper.getResponseTo(newsRepository.save(news));
    }

    @CacheEvict(cacheNames = "news", allEntries = true)
    @Override
    public NewsResponseTo update(NewsRequestTo newsTo) {
        newsRepository
                .findById(newsMapper.getNews(newsTo).getId())
                .orElseThrow(() -> newsNotFoundException(newsMapper.getNews(newsTo).getId()));
        return newsMapper.getResponseTo(newsRepository.save(newsMapper.getNews(newsTo)));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "news", key = "#id"),
            @CacheEvict(cacheNames = "news", allEntries = true) })
    @Override
    public void removeById(Long id) {
        News news = newsRepository
                .findById(id)
                .orElseThrow(() -> newsNotFoundException(id));
        newsRepository.delete(news);
    }

    private static ResourceNotFoundException newsNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find news with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 53);
    }

    private static ResourceStateException newsStateException() {
        return new ResourceStateException("Failed to create/update news with specified credentials", HttpStatus.CONFLICT.value() * 100 + 54);
    }
}
