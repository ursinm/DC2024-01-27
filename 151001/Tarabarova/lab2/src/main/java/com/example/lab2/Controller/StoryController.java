package com.example.lab2.Controller;

import com.example.lab2.DTO.StoryRequestTo;
import com.example.lab2.DTO.StoryResponseTo;
import com.example.lab2.Service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class StoryController {
    @Autowired
    StoryService storyService;

    @GetMapping(value = "storys", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> readAll(
            @RequestParam (required = false, defaultValue = "0") Integer pageInd,
            @RequestParam (required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam (required = false, defaultValue = "id") String sortedBy,
            @RequestParam (required = false, defaultValue = "desc") String direction)
    {
        List<StoryResponseTo> list = storyService.readAll(pageInd, numOfElem, sortedBy, direction);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "storys/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        StoryResponseTo story = storyService.read(id);
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    @PostMapping(value = "storys")
    public ResponseEntity<?> create(@RequestBody StoryRequestTo storyRequestTo) {
        StoryResponseTo storyResponseTo = storyService.create(storyRequestTo);
        return new ResponseEntity<>(storyResponseTo, HttpStatus.CREATED);
    }

    @PutMapping(value = "storys")
    public ResponseEntity<?> update(@RequestBody StoryRequestTo storyRequestTo) {
        StoryResponseTo storyResponseTo = storyService.update(storyRequestTo, storyRequestTo.getId());
        return new ResponseEntity<>(storyResponseTo, HttpStatus.OK);
    }

    @DeleteMapping(value = "storys/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean res = storyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
