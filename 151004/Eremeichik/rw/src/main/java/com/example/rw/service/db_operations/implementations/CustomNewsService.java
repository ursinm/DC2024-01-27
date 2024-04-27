package com.example.rw.service.db_operations.implementations;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.entity.implementations.News;
import com.example.rw.model.entity.implementations.User;
import com.example.rw.repository.interfaces.NewsRepository;
import com.example.rw.service.db_operations.interfaces.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomNewsService implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    @Cacheable(value = "news", key = "#id")
    public News findById(Long id) throws EntityNotFoundException {
        return newsRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public void save(News entity) {
        newsRepository.save(entity);
    }

    @Override
    @CacheEvict(cacheNames = "news", key = "#id")
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = newsRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            newsRepository.deleteById(id);
        }
    }

    @Override
    @CachePut(value = "news", key = "#entity.id")
    public News update(News entity) {
        boolean wasUpdated = newsRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            newsRepository.save(entity);
        }
        return entity;
    }
}
