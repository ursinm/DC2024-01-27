package by.bsuir.newsapi.controller;

import by.bsuir.newsapi.model.request.TagRequestTo;
import by.bsuir.newsapi.model.response.TagResponseTo;
import by.bsuir.newsapi.service.TagService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags")
@Data
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseTo> findAll() {
        return tagService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponseTo create(@Valid @RequestBody TagRequestTo dto) {
        return tagService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagResponseTo get(@Valid @PathVariable("id") Long id) {
        return tagService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TagResponseTo update(@Valid @RequestBody TagRequestTo dto) {
        return tagService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        tagService.removeById(id);
    }

}
