package by.bsuir.messageapp.controller;

import by.bsuir.messageapp.model.request.MarkerRequestTo;
import by.bsuir.messageapp.model.response.MarkerResponseTo;
import by.bsuir.messageapp.service.MarkerService;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo findById(@Valid @PathVariable("id") Long id) {
        return markerService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MarkerResponseTo> findAll() {
        return markerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo create(@Valid @RequestBody MarkerRequestTo request) {
        return markerService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo update(@Valid @RequestBody MarkerRequestTo request) {
        return markerService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@Valid @PathVariable("id") Long id) {
        markerService.removeById(id);
    }
}
