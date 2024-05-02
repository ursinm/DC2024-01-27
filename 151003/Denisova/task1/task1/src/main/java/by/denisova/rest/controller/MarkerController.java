package by.denisova.rest.controller;

import by.denisova.rest.dto.request.CreateMarkerDto;
import by.denisova.rest.dto.request.UpdateMarkerDto;
import by.denisova.rest.dto.response.MarkerResponseDto;
import by.denisova.rest.facade.MarkerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/markers")
public class MarkerController {

    private final MarkerFacade markerFacade;

    @GetMapping("/{id}")
    public MarkerResponseDto findMarkerById(@PathVariable("id") Long id) {
        return markerFacade.findById(id);
    }

    @GetMapping
    public List<MarkerResponseDto> findAll() {
        return markerFacade.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MarkerResponseDto saveMarker(@RequestBody @Valid CreateMarkerDto markerRequest) {
        return markerFacade.save(markerRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMarker(@PathVariable("id") Long id) {
        markerFacade.delete(id);
    }

    @PutMapping
    public MarkerResponseDto updateMarker(@RequestBody @Valid UpdateMarkerDto markerRequest) {
        return markerFacade.update(markerRequest);
    }
}
