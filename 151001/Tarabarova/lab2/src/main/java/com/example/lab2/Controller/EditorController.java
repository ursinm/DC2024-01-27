package com.example.lab2.Controller;

import com.example.lab2.DTO.EditorRequestTo;
import com.example.lab2.DTO.EditorResponseTo;
import com.example.lab2.Service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class EditorController {
    @Autowired
    EditorService editorService;

    @PostMapping(value = "editors")
    public ResponseEntity<?> create(@RequestBody EditorRequestTo editor) {
        EditorResponseTo cr = editorService.create(editor);
        return new ResponseEntity<>(cr, HttpStatus.CREATED);
    }

    @GetMapping(value = "editors", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<EditorResponseTo>> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<EditorResponseTo> editors = editorService.readAll(pageInd, numOfElem, sortedBy, direction);
        return new ResponseEntity<>(editors, HttpStatus.OK);
    }

    @GetMapping(value = "editors/{id}")
    public ResponseEntity<EditorResponseTo> read(@PathVariable(name = "id") int id) {
        final EditorResponseTo editor = editorService.read(id);
        return new ResponseEntity<>(editor, HttpStatus.OK);
    }

    @PutMapping(value = "editors")
    public ResponseEntity<?> update(@RequestBody EditorRequestTo editor) {
        final EditorResponseTo updated = editorService.update(editor, editor.getId());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "editors/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = editorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
