package by.bsuir.news.controller;

import by.bsuir.news.dto.request.EditorRequestTo;
import by.bsuir.news.dto.response.EditorResponseTo;
import by.bsuir.news.entity.Editor;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.service.EditorService;
import by.bsuir.news.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editors")
@ControllerAdvice
public class EditorController {
    @Autowired
    private EditorService editorService = new EditorService();

    @GetMapping
    public ResponseEntity getEditors() {
        try {
            return ResponseEntity.ok(editorService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneEditor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(editorService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity saveEditor(@RequestBody EditorRequestTo editor) {
        try {
            return new ResponseEntity(editorService.create(editor), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(editor, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping()
    public ResponseEntity updateEditor(@RequestBody EditorRequestTo editor) {
        try {
            return ResponseEntity.ok(editorService.update(editor));
        } catch (ClientException e) {
            return new ResponseEntity(editor, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(editor, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEditor(@PathVariable Long id) {
        try {
            return new ResponseEntity(editorService.delete(id), HttpStatus.NO_CONTENT);
        } catch (ClientException e) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}
