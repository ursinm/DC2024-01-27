package by.haritonenko.jpa.controller;

import by.haritonenko.jpa.dto.request.CreateAuthorDto;
import by.haritonenko.jpa.dto.request.UpdateAuthorDto;
import by.haritonenko.jpa.dto.response.AuthorResponseDto;
import by.haritonenko.jpa.facade.AuthorFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/authors")
public class AuthorController {

    private final AuthorFacade authorFacade;

    @GetMapping("/{id}")
    public AuthorResponseDto findAuthorById(@PathVariable("id") Long id) {
        return authorFacade.findById(id);
    }

    @GetMapping
    public List<AuthorResponseDto> findAll() {
        return authorFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorResponseDto saveAuthor(@RequestBody @Valid CreateAuthorDto authorRequest) {
        return authorFacade.save(authorRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorFacade.delete(id);
    }

    @PutMapping
    public AuthorResponseDto updateAuthor(@RequestBody @Valid UpdateAuthorDto authorRequest) {
        return authorFacade.update(authorRequest);
    }
}
