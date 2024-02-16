package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.dtos.response.AuthorResponseTo;
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
    public AuthorResponseTo createAuthor(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "first_name") String firstName,
            @RequestParam(value = "last_name") String lastName) {

        AuthorRequestTo authorRequestTo = new AuthorRequestTo(
                login,
                password,
                firstName,
                lastName
        );

        return authorService.create(authorRequestTo);
    }

    @GetMapping("api/v1.0/authors")
    public List<AuthorResponseTo> getAuthors() {
        return authorService.getAll();
    }

    @GetMapping("api/v1.0/authors/{id}")
    public AuthorResponseTo getAuthor(@PathVariable long id) {
        return authorService.get(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/v1.0/authors")
    public AuthorResponseTo deleteAuthor(@RequestParam(value = "id") long id) {
        return authorService.delete(id);
    }

    @PutMapping("api/v1.0/authors")
    public AuthorResponseTo updateAuthor(@RequestBody AuthorRequestTo author) {
        return authorService.update(author);
    }

}
