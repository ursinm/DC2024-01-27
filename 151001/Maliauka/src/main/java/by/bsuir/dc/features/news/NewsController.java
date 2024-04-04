package by.bsuir.dc.features.news;

import by.bsuir.dc.features.news.dto.NewsRequestDto;
import by.bsuir.dc.features.news.dto.NewsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsResponseDto> createNews(@RequestBody NewsRequestDto newsRequestDto)
    {
        var news = newsService.createNews(newsRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @GetMapping
    public ResponseEntity<List<NewsResponseDto>> getAll() {
        var news = newsService.getAll();
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long newsId) {
        var news = newsService.getById(newsId);
        return ResponseEntity.ok(news);
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<NewsResponseDto> updateNewsById(@PathVariable Long newsId,
                                                          @RequestBody NewsRequestDto newsRequestDto) {
        var news = newsService.updateNewsById(newsId, newsRequestDto);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<NewsResponseDto> deleteNewsById(@PathVariable Long newsId) {
        var news = newsService.deleteNewsById(newsId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(news);
    }
}
