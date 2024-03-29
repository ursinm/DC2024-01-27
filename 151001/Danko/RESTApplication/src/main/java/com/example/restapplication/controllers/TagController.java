package com.example.restapplication.controllers;

import com.example.restapplication.dto.TagRequestTo;
import com.example.restapplication.dto.TagResponseTo;
import com.example.restapplication.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponseTo>> getAll() {
        return ResponseEntity.status(200).body(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(tagService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TagResponseTo> save(@RequestBody TagRequestTo tag) {
        TagResponseTo tagToSave = tagService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagToSave);
    }

    @PutMapping()
    public ResponseEntity<TagResponseTo> update(@RequestBody TagRequestTo tag) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.update(tag));
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<TagResponseTo> getByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(tagService.getByStoryId(id));
    }
}
