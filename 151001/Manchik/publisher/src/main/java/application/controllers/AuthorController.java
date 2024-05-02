package application.controllers;

import application.dto.AuthorRequestTo;
import application.dto.AuthorResponseTo;
import application.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/authors")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseTo>> getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                      @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(authorService.getAll(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(authorService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AuthorResponseTo> save(@RequestBody AuthorRequestTo author) {
        AuthorResponseTo authorToSave = authorService.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorToSave);
    }

    @PutMapping
    public ResponseEntity<AuthorResponseTo> update(@RequestBody AuthorRequestTo author) {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.update(author));
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<AuthorResponseTo> getByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(authorService.getByStoryId(id));
    }
}
