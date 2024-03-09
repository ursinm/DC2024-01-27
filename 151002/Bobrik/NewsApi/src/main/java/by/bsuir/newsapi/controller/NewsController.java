package by.bsuir.newsapi.controller;

import by.bsuir.newsapi.model.request.EditorRequestTo;
import by.bsuir.newsapi.model.request.NewsRequestTo;
import by.bsuir.newsapi.model.response.EditorResponseTo;
import by.bsuir.newsapi.model.response.NewsResponseTo;
import by.bsuir.newsapi.service.NewsService;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponseTo> findAll() {
        return newsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseTo create(@Valid @RequestBody NewsRequestTo dto) {
        return newsService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo get(@Valid @PathVariable("id") Long id) {
        return newsService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseTo update(@Valid @RequestBody NewsRequestTo dto) {
        return newsService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        newsService.removeById(id);
    }
}
