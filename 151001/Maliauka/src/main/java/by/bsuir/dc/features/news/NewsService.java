package by.bsuir.dc.features.news;


import by.bsuir.dc.exceptions.EntityNotFoundException;
import by.bsuir.dc.exceptions.ErrorMessages;
import by.bsuir.dc.features.editor.EditorRepository;
import by.bsuir.dc.features.news.dto.NewsRequestDto;
import by.bsuir.dc.features.news.dto.NewsResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class NewsService {
    private final NewsMapper newsMapper;
    private final EditorRepository editorRepository;
    private final NewsRepository newsRepository;

    public NewsResponseDto createNews(@Valid NewsRequestDto newsRequestDto) {
       var doseExist = editorRepository.existsById(newsRequestDto.editorId());
       if (!doseExist){
           throw new EntityNotFoundException(ErrorMessages.newsNotFound);
       }

       var news = newsMapper.toEntity(newsRequestDto);
       news = newsRepository.save(news);
       return newsMapper.toDto(news);
    }

    public List<NewsResponseDto> getAll() {
        var news = newsRepository.findAll();
        return newsMapper.toDtoList(news);
    }

    public NewsResponseDto getById(@Min(1) @Max(Long.MAX_VALUE) Long newsId) {
        var news = newsRepository.findById(newsId).orElseThrow(
                () -> new EntityNotFoundException("News with such id does not exists"));
        return newsMapper.toDto(news);
    }

    public NewsResponseDto updateNewsById(
            @Min(1) @Max(Long.MAX_VALUE) Long newsId,
            @Valid NewsRequestDto newsRequestDto
    ) {
        var doesExist = newsRepository.existsById(newsId);
        if (doesExist) {
            throw new EntityNotFoundException("News with such id does not exists");
        }

        var news = newsMapper.toEntity(newsRequestDto);
        news.setId(newsId);
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    public NewsResponseDto deleteNewsById(@Min(1) @Max(Long.MAX_VALUE) Long newsId) {
        var news = newsRepository.findById(newsId).orElseThrow(
                () -> new EntityNotFoundException("News with such id does not exists"));

        newsRepository.deleteById(newsId);

        return newsMapper.toDto(news);
    }
}
