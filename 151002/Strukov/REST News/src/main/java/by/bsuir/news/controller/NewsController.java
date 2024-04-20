package by.bsuir.news.controller;

import by.bsuir.news.dto.request.NewsRequestTo;
import by.bsuir.news.dto.response.NewsResponseTo;
import by.bsuir.news.entity.News;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.service.GenericService;
import by.bsuir.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService = new NewsService();

    @GetMapping
    public ResponseEntity getAllNews() {
        try {
            return ResponseEntity.ok(newsService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneNews(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity saveNews(@RequestBody NewsRequestTo news) {
        try {
            return new ResponseEntity(newsService.create(news), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(news, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping()
    public ResponseEntity updateNews(@RequestBody NewsRequestTo news) {
        try {
            return ResponseEntity.ok(newsService.update(news));
        } catch (ClientException ce) {
            return new ResponseEntity(ce.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(news, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        try {
            return new ResponseEntity(newsService.delete(id), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
