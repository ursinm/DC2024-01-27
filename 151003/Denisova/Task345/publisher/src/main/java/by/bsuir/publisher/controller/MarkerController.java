package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.request.MarkerRequestTo;
import by.bsuir.publisher.model.response.MarkerResponseTo;
import by.bsuir.publisher.service.MarkerService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/markers")
@Data
@RequiredArgsConstructor
public class MarkerController {
    private final MarkerService markerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MarkerResponseTo> findAll() {
        return markerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo create(@Valid @RequestBody MarkerRequestTo dto) {
        return markerService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo get(@Valid @PathVariable("id") Long id) {
        return markerService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo update(@Valid @RequestBody MarkerRequestTo dto) {
        return markerService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        markerService.removeById(id);
    }

}
