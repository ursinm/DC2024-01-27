package com.example.distributedcomputing.controller;

import com.example.distributedcomputing.model.request.EditorRequestTo;
import com.example.distributedcomputing.model.response.EditorResponseTo;
import com.example.distributedcomputing.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/editors")
@RequiredArgsConstructor
public class EditorController {
	private final EditorService editorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorResponseTo create(@RequestBody EditorRequestTo editorRequestTo) {
        return editorService.save(editorRequestTo);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo update(@RequestBody EditorRequestTo editorRequestTo) {
        return editorService.update(editorRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
       editorService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<EditorResponseTo> getAll() {
        return editorService.getAll();
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo getById(@PathVariable Long id) {
        return editorService.getById(id);
    }
}
