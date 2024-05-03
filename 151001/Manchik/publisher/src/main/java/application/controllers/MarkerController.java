package application.controllers;

import application.dto.MarkerRequestTo;
import application.dto.MarkerResponseTo;
import application.services.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/markers")
public class MarkerController {
    @Autowired
    MarkerService markerService;

    @GetMapping
    public ResponseEntity<List<MarkerResponseTo>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
    ) {
        return ResponseEntity.status(200).body(markerService.getAll(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkerResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(markerService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        markerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MarkerResponseTo> save(@RequestBody MarkerRequestTo marker) {
        MarkerResponseTo markerToSave = markerService.save(marker);
        return ResponseEntity.status(HttpStatus.CREATED).body(markerToSave);
    }

    @PutMapping()
    public ResponseEntity<MarkerResponseTo> update(@RequestBody MarkerRequestTo marker) {
        return ResponseEntity.status(HttpStatus.OK).body(markerService.update(marker));
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<List<MarkerResponseTo>> getByStoryId(@PathVariable Long id){
        return ResponseEntity.status(200).body(markerService.getByStoryId(id));
    }
}
