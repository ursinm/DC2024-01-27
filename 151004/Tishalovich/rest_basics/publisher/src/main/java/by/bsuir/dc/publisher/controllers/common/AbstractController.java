package by.bsuir.dc.publisher.controllers.common;

import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
public class AbstractController<I, E> {

    private final AbstractService<I, E> service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public E createAuthor(@RequestBody I requestTo) throws ApiException {
        return service.create(requestTo);
    }

    @GetMapping("")
    public List<E> getAuthors() throws ApiException {
        return service.getAll();
    }

    @GetMapping("{id}")
    public E getAuthor(@PathVariable Long id) throws ApiException {
        return service.get(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable(value = "id") Long id) throws ApiException {
        service.delete(id);
    }

    @PutMapping("")
    public E updateAuthor(@RequestBody I author) throws ApiException {
        return service.update(author);
    }

}
