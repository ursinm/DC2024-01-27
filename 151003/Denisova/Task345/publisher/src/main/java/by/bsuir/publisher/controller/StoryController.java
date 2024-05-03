package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.request.StoryRequestTo;
import by.bsuir.publisher.model.response.StoryResponseTo;
import by.bsuir.publisher.service.StoryService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/storys")
@Data
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoryResponseTo> findAll() {
        return storyService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoryResponseTo create(@Valid @RequestBody StoryRequestTo dto) {
        return storyService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo get(@Valid @PathVariable("id") Long id) {
        return storyService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo update(@Valid @RequestBody StoryRequestTo dto) {
        return storyService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        storyService.removeById(id);
    }
}
