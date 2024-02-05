package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import by.bsuir.poit.dc.rest.api.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.ErrorDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsLabelDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
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
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping("")
    public ResponseEntity<NewsDto> publishNews(
	@RequestBody @Validated(Create.class) UpdateNewsDto dto
    ) {
	var response = newsService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getNews(
	@RequestParam(value = "label", required = false) String label,
	@RequestParam(value = "userId", required = false) Long userId
    ) {
	//todo: add custom parameters handler to perform such functionality
	if (label != null && userId != null) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(conflictParameters());
	}
	List<NewsDto> newsList;
	if (label != null) {
	    newsList = newsService.getNewsByLabel(label);
	} else if (userId != null) {
	    newsList = newsService.getNewsByUserId(userId);
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

    @PatchMapping("/{newsId}")
    public NewsDto updateNewsById(
	@PathVariable long newsId,
	@RequestBody @Validated(Update.class) UpdateNewsDto dto
    ) {
	return newsService.update(newsId, dto);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<?> deleteNewsById(
	@PathVariable long newsId
    ) {
	boolean isDeleted = newsService.delete(newsId);
	HttpStatus status = isDeleted ?
				HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }

    @PostMapping("/{newsId}/notes")
    public ResponseEntity<NoteDto> createNewsNote(
	@PathVariable long newsId,
	@RequestBody @Validated(Create.class) UpdateNoteDto dto
    ) {
	var response = newsService.createNote(newsId, dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    public ResponseEntity<?> deleteNewsLabel(
	@PathVariable long newsId,
	@PathVariable long labelId
    ) {
	boolean isDeleted = newsService.detachLabelById(newsId, labelId);
	var status = isDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }

    private static ErrorDto conflictParameters() {
	return ErrorDto.builder()
		   .errorCode(122)
		   .errorMessage("The provided parameters should be passed separately")
		   .build();
    }

}
