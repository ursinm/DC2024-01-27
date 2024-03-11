package com.example.rw.controller;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.dto.news.NewsRequestTo;
import com.example.rw.model.dto.news.NewsResponseTo;
import com.example.rw.model.entity.implementations.News;
import com.example.rw.service.db_operations.interfaces.NewsService;
import com.example.rw.service.dto_converter.interfaces.NewsToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.request.prefix}/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsToConverter newsToConverter;

    @PostMapping()
    public ResponseEntity<NewsResponseTo> createNews(@RequestBody @Valid NewsRequestTo newsRequestTo) {
        News news = newsToConverter.convertToEntity(newsRequestTo);
        newsService.save(news);
        NewsResponseTo newsResponseTo = newsToConverter.convertToDto(news);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsResponseTo);
    }

    @GetMapping()
    public ResponseEntity<List<NewsResponseTo>> receiveAllNews() {
        List<News> newsList = newsService.findAll();
        List<NewsResponseTo> responseList = newsList.stream()
                .map(newsToConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseTo> receiveNewsById(@PathVariable Long id) {
        News news = newsService.findById(id);
        NewsResponseTo newsResponseTo = newsToConverter.convertToDto(news);
        return ResponseEntity.ok(newsResponseTo);
    }

    @PutMapping()
    public ResponseEntity<NewsResponseTo> updateNews(@RequestBody @Valid NewsRequestTo newsRequestTo) {
        News news = newsToConverter.convertToEntity(newsRequestTo);
        newsService.save(news);
        NewsResponseTo newsResponseTo = newsToConverter.convertToDto(news);
        return ResponseEntity.ok(newsResponseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsById(@PathVariable Long id) {
        try {
            newsService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
