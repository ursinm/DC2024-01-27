package com.example.restapplication.controllers;

import com.example.restapplication.dto.StoryRequestTo;
import com.example.restapplication.dto.StoryResponseTo;
import com.example.restapplication.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/storys")
public class StoryController {
    @Autowired
    StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryResponseTo>> getAll() {
        return ResponseEntity.status(200).body(storyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(storyService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        storyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<StoryResponseTo> save(@RequestBody StoryRequestTo story) {
        StoryResponseTo storyToSave = storyService.save(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(storyToSave);
    }

    @PutMapping()
    public ResponseEntity<StoryResponseTo> update(@RequestBody StoryRequestTo story) {
        return ResponseEntity.status(HttpStatus.OK).body(storyService.update(story));
    }

    @GetMapping("/info")
    public ResponseEntity<List<StoryResponseTo>> getByData(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) String userLogin,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(storyService.getByData(tagName, tagId, userLogin, title, content));
    }
}
