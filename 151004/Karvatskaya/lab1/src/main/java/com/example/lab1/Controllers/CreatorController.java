package com.example.lab1.Controllers;

import com.example.lab1.DTO.CreatorRequestTo;
import com.example.lab1.DTO.CreatorResponseTo;
import com.example.lab1.Service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class CreatorController {
    @Autowired
    CreatorService creatorService;

    @PostMapping(value = "creators")
    public ResponseEntity<?> create(@RequestBody CreatorRequestTo creator) {
        CreatorResponseTo cr = creatorService.create(creator);
        return new ResponseEntity<>(cr, HttpStatus.CREATED);
    }

    @GetMapping(value = "creators", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<CreatorResponseTo>> read() {
        final List<CreatorResponseTo> creators = creatorService.readAll();
        return new ResponseEntity<>(creators, HttpStatus.OK);
    }

    @GetMapping(value = "creators/{id}")
    public ResponseEntity<CreatorResponseTo> read(@PathVariable(name = "id") int id) {
        final CreatorResponseTo creator = creatorService.read(id);
        return new ResponseEntity<>(creator, HttpStatus.OK);
    }

    @PutMapping(value = "creators")
    public ResponseEntity<?> update(@RequestBody CreatorRequestTo creator) {
        final CreatorResponseTo updated = creatorService.update(creator, creator.getId());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "creators/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = creatorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
