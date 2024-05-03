package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.TagRequestTo;
import com.example.distributedcomputing.model.response.TagResponseTo;
import com.example.distributedcomputing.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponseTo create(@RequestBody TagRequestTo tagRequestTo) {
        return tagService.save(tagRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<TagResponseTo> getAll() {
        return tagService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public TagResponseTo getById(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public TagResponseTo update(@RequestBody TagRequestTo tagRequestTo) {
        return tagService.update(tagRequestTo);
    }
}
