package by.bsuir.messageapp.controller;

import by.bsuir.messageapp.model.request.StoryRequestTo;
import by.bsuir.messageapp.model.response.StoryResponseTo;
import by.bsuir.messageapp.service.StoryService;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo findById(@Valid @PathVariable("id") Long id) {
        return storyService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoryResponseTo> findAll() {
        return storyService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoryResponseTo create(@Valid @RequestBody StoryRequestTo request) {
        return storyService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo update(@Valid @RequestBody StoryRequestTo request) {
        return storyService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@Valid @PathVariable("id") Long id) {
        storyService.removeById(id);
    }
}
