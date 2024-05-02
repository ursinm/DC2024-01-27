package org.example.publisher.api.Controllers;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.story.dto.StoryRequestTo;
import org.example.publisher.impl.story.dto.StoryResponseTo;
import org.example.publisher.impl.story.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/storys")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StoryResponseTo> getStorys() {
        return storyService.getStorys();
    }

    @GetMapping("/{id}")
    public StoryResponseTo getStoryById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return storyService.getStoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoryResponseTo createStory(@Valid @RequestBody StoryRequestTo storyRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        return storyService.saveStory(storyRequestTo);
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public StoryResponseTo updateStory(@Valid @RequestBody StoryRequestTo story) throws EntityNotFoundException, DuplicateEntityException {
        return storyService.updateStory(story);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStory(@PathVariable BigInteger id) throws EntityNotFoundException {
        storyService.deleteStory(id);
    }
}
