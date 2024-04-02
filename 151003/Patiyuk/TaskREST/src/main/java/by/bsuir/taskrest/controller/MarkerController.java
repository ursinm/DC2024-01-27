package by.bsuir.taskrest.controller;

import by.bsuir.taskrest.dto.request.MarkerRequestTo;
import by.bsuir.taskrest.dto.response.MarkerResponseTo;
import by.bsuir.taskrest.service.MarkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/markers")
public class MarkerController {

    private final MarkerService markerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MarkerResponseTo> getAllMarkers() {
        return markerService.getAllMarkers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo getMarkerById(@PathVariable Long id) {
        return markerService.getMarkerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkerResponseTo createMarker(@Valid @RequestBody MarkerRequestTo marker) {
        return markerService.createMarker(marker);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo updateMarker(@Valid @RequestBody MarkerRequestTo marker) {
        return markerService.updateMarker(marker);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkerResponseTo updateMarker(@PathVariable Long id, @Valid @RequestBody MarkerRequestTo marker) {
        return markerService.updateMarker(id, marker);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarker(@PathVariable Long id) {
        markerService.deleteMarker(id);
    }
}
