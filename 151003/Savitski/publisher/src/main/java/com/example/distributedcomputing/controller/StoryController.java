package com.example.distributedcomputing.controller;


import com.example.distributedcomputing.model.request.StoryRequestTo;
import com.example.distributedcomputing.model.response.StoryResponseTo;
import com.example.distributedcomputing.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/storys")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoryResponseTo create(@RequestBody StoryRequestTo storyRequestTo) {
        return storyService.save(storyRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storyService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<StoryResponseTo> getAll() {
        return storyService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo getById(@PathVariable Long id) {
        return storyService.getById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo update(@RequestBody StoryRequestTo storyRequestTo) {
        return storyService.update(storyRequestTo);
    }
}
