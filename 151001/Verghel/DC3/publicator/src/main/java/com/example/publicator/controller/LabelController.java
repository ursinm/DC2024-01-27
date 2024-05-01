package com.example.publicator.controller;

import com.example.publicator.dto.LabelRequestTo;
import com.example.publicator.dto.LabelResponseTo;
import com.example.publicator.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1.0/")
public class LabelController {
    @Autowired
    LabelService labelService;

    @PostMapping(value = "labels")
    public ResponseEntity<?> create(@RequestBody LabelRequestTo labelRequestTo) {
        LabelResponseTo label = labelService.create(labelRequestTo);
        return new ResponseEntity<>(label, HttpStatus.CREATED);
    }

    @GetMapping(value = "labels", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<LabelResponseTo> list = labelService.readAll(pageInd, numOfElem, sortedBy, direction);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "labels/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        LabelResponseTo label = labelService.read(id);
        return new ResponseEntity<>(label, HttpStatus.OK);
    }

    @PutMapping(value = "labels")
    public ResponseEntity<?> update(@RequestBody LabelRequestTo labelRequestTo) {
        LabelResponseTo label = labelService.update(labelRequestTo, labelRequestTo.getId());
        return new ResponseEntity<>(label, HttpStatus.OK);
    }

    @DeleteMapping(value = "labels/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = labelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
