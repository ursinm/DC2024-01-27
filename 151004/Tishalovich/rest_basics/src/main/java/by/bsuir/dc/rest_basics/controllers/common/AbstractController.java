package by.bsuir.dc.rest_basics.controllers.common;

import by.bsuir.dc.rest_basics.services.common.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
public class AbstractController<I, E> {

    private final AbstractService<I, E> service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public E createAuthor(@RequestBody I requestTo) {
        return service.create(requestTo);
    }

    @GetMapping("")
    public List<E> getAuthors() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public E getAuthor(@PathVariable Long id) {
        return service.get(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public E deleteAuthor(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @PutMapping("")
    public E updateAuthor(@RequestBody I author) {
        return service.update(author);
    }

}
