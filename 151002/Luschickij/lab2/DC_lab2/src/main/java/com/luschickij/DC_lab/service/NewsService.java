package com.luschickij.DC_lab.service;

import com.luschickij.DC_lab.dao.repository.NewsRepository;
import com.luschickij.DC_lab.model.entity.News;
import com.luschickij.DC_lab.model.request.NewsRequestTo;
import com.luschickij.DC_lab.model.response.NewsResponseTo;
import com.luschickij.DC_lab.service.exceptions.ResourceNotFoundException;
import com.luschickij.DC_lab.service.exceptions.ResourceStateException;
import com.luschickij.DC_lab.service.mapper.NewsMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class NewsService implements IService<NewsRequestTo, NewsResponseTo> {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsResponseTo findById(Long id) {
        return newsRepository.findById(id).map(newsMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<NewsResponseTo> findAll() {
        return newsMapper.getListResponse(newsRepository.findAll());
    }

    @Override
    public NewsResponseTo create(NewsRequestTo request) {
        NewsResponseTo response = newsMapper.getResponse(newsRepository.save(newsMapper.getNews(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @Override
    public NewsResponseTo update(NewsRequestTo request) {
        News news = newsMapper.getNews(request);
        news = newsRepository.save(news);
        NewsResponseTo response = newsMapper.getResponse(news);

        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @Override
    public void removeById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(NewsService::removeException);

        newsRepository.delete(news);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 41, "Can't find news by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 42, "Can't create news");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 43, "Can't update news");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 44, "Can't remove news");
    }
}
