package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.response.ErrorDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsLabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.NewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsDto> publishNews(
	@RequestBody @Validated(Create.class) UpdateNewsDto dto
    ) {
	var response = newsService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getNews(
	@RequestParam(value = "label", required = false) String label,
	@RequestParam(value = "userId", required = false) Long userId,
	@RequestParam(value = "offset", required = false) Long offset,
	@RequestParam(value = "limit", required = false) Integer limit
    ) {
	if (label != null && userId != null) {
	    return conflictParameters(HttpStatus.BAD_REQUEST);
	}
	List<NewsDto> newsList;
	if (label != null) {
	    newsList = newsService.getNewsByLabel(label);
	} else if (userId != null) {
	    newsList = newsService.getNewsByUserId(userId);
	} else if (limit != null && offset != null) {
	    newsList = newsService.getByOffsetAndLimit(offset, limit);
	} else {
	    newsList = newsService.getAll();
	}
	return ResponseEntity.ok(newsList);
    }

    @GetMapping("/{newsId}")
    public NewsDto getNewsById(
	@PathVariable long newsId
    ) {
	return newsService.getById(newsId);
    }

    @PutMapping
    public NewsDto updateNewsById(
	@RequestBody @Validated(Update.class) UpdateNewsDto dto
    ) {
	long newsId = dto.id();
	return newsService.update(newsId, dto);
    }

    @DeleteMapping("/{newsId}")
    public Object deleteNewsById(
	@PathVariable long newsId
    ) {
	return newsService.delete(newsId);
    }

    @GetMapping("/{newsId}/notes")
    public List<NoteDto> getNewsNotes(
	@PathVariable long newsId
    ) {
	return newsService.getNotesByNewsId(newsId);
    }

    @PostMapping("/{newsId}/labels")
    public void createNewsLabel(
	@PathVariable long newsId,
	@RequestBody @Validated(Create.class) UpdateNewsLabelDto dto
    ) {
	newsService.attachLabelById(newsId, dto);
    }

    @GetMapping("/{newsId}/labels")
    public List<LabelDto> getNewsLabels(
	@PathVariable long newsId
    ) {
	return newsService.getLabelsByNewsId(newsId);
    }

    @DeleteMapping("/{newsId}/labels/{labelId}")
    public Object deleteNewsLabel(
	@PathVariable long newsId,
	@PathVariable long labelId
    ) {
	return newsService.detachLabelById(newsId, labelId);
    }

    private static ResponseEntity<ErrorDto> conflictParameters(
	HttpStatus status
    ) {
	ErrorDto dto = ErrorDto.builder()
			   .errorCode(ErrorDto.codeOf(status, 122))
			   .errorMessage("The provided parameters should be passed separately")
			   .build();
	return ResponseEntity.status(status).body(dto);
    }

}
