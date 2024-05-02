package by.bsuir.publisher.controllers;

import by.bsuir.publisher.dto.requests.MarkerRequestDto;
import by.bsuir.publisher.dto.responses.MarkerResponseDto;
import by.bsuir.publisher.exceptions.EntityExistsException;
import by.bsuir.publisher.exceptions.Notes;
import by.bsuir.publisher.exceptions.NoEntityExistsException;
import by.bsuir.publisher.services.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/markers")
@RequiredArgsConstructor
public class MarkerController {
    private final MarkerService markerService;

    @PostMapping
    public ResponseEntity<MarkerResponseDto> create(@RequestBody MarkerRequestDto marker) throws EntityExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(markerService.create(marker));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkerResponseDto> read(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(markerService.read(id).orElseThrow(() ->
                new NoEntityExistsException(Notes.NoEntityExistsException)));
    }

    @GetMapping
    public ResponseEntity<List<MarkerResponseDto>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(markerService.readAll());
    }

    @PutMapping
    public ResponseEntity<MarkerResponseDto> update(@RequestBody MarkerRequestDto marker) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(markerService.update(marker));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws NoEntityExistsException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(markerService.delete(id));
    }
}
