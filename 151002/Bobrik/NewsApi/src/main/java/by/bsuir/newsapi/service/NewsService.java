package by.bsuir.newsapi.service;

import by.bsuir.newsapi.dao.NewsRepository;
import by.bsuir.newsapi.model.entity.Comment;
import by.bsuir.newsapi.model.entity.News;
import by.bsuir.newsapi.model.request.NewsRequestTo;
import by.bsuir.newsapi.model.response.NewsResponseTo;
import by.bsuir.newsapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.newsapi.service.exceptions.ResourceStateException;
import by.bsuir.newsapi.service.mapper.NewsMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class NewsService implements RestService<NewsRequestTo, NewsResponseTo> {
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @Override
    public List<NewsResponseTo> findAll() {
        return newsMapper.getListResponseTo(newsRepository.findAll());
    }

    @Override
    public NewsResponseTo findById(Long id) {
        return newsMapper.getResponseTo(newsRepository
                .findById(id)
                .orElseThrow(() -> newsNotFoundException(id)));
    }

    @Override
    public NewsResponseTo create(NewsRequestTo newsTo) {
        News news = newsMapper.getNews(newsTo);
        news.setCreated(LocalDateTime.now());
        news.setModified(news.getCreated());
        return newsMapper.getResponseTo(newsRepository.save(news));
    }

    @Override
    public NewsResponseTo update(NewsRequestTo newsTo) {
        newsRepository
                .findById(newsMapper.getNews(newsTo).getId())
                .orElseThrow(() -> newsNotFoundException(newsMapper.getNews(newsTo).getId()));
        return newsMapper.getResponseTo(newsRepository.save(newsMapper.getNews(newsTo)));
    }

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
