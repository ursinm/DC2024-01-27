package application.controllers;

import application.dto.StoryRequestTo;
import application.dto.StoryResponseTo;
import application.services.StoryService;
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
    public ResponseEntity<List<StoryResponseTo>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
    ) {
        return ResponseEntity.status(200).body(storyService.getAll(pageNumber, pageSize, sortBy, sortOrder));
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
            @RequestParam(required = false) List<String> markerName,
            @RequestParam(required = false) List<Long> markerId,
            @RequestParam(required = false) String authorLogin,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(storyService.getByData(markerName, markerId, authorLogin, title, content));
    }
}
