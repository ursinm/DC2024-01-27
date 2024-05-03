package com.luschickij.DC_lab.controller;

import com.luschickij.DC_lab.model.request.NewsRequestTo;
import com.luschickij.DC_lab.model.response.NewsResponseTo;
import com.luschickij.DC_lab.service.NewsService;
import com.luschickij.DC_lab.service.NewsService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/news")
@Data
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo findById(@Valid @PathVariable("id") Long id) {
        return newsService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponseTo> findAll() {
        return newsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseTo create(@Valid @RequestBody NewsRequestTo request) {
        return newsService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo update(@Valid @RequestBody NewsRequestTo request) {
        return newsService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@Valid @PathVariable("id") Long id) {
        newsService.removeById(id);
    }
}
