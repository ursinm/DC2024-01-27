package by.bsuir.springapi.controller;

import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import by.bsuir.springapi.service.CreatorService;
import by.bsuir.springapi.service.RestService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/creators")
@Data
@RequiredArgsConstructor
public class CreatorController {
    private final RestService<CreatorRequestTo, CreatorResponseTo> creatorService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreatorResponseTo> findAll() {
        return creatorService.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatorResponseTo create(@Valid @RequestBody CreatorRequestTo dto) {
        return creatorService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo get(@Valid @PathVariable("id") Long id) {
        return creatorService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CreatorResponseTo update(@Valid @RequestBody CreatorRequestTo dto) {
        return creatorService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        creatorService.removeById(id);
    }
}
