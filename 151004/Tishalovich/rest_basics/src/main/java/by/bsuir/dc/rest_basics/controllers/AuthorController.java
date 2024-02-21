package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/v1.0/authors")
    public AuthorResponseTo createAuthor(@RequestBody AuthorRequestTo author) {
        return authorService.create(author);
    }

    @GetMapping("api/v1.0/authors")
    public List<AuthorResponseTo> getAuthors() {
        return authorService.getAll();
    }

    @GetMapping("api/v1.0/authors/{id}")
    public AuthorResponseTo getAuthor(@PathVariable Long id) {
        return authorService.get(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/v1.0/authors/{id}")
    public AuthorResponseTo deleteAuthor(@PathVariable(value = "id") Long id) {
        return authorService.delete(id);
    }

    @PutMapping("api/v1.0/authors/{id}")
    public AuthorResponseTo updateAuthor(@RequestBody AuthorRequestTo author) {
        return authorService.update(author);
    }

}
