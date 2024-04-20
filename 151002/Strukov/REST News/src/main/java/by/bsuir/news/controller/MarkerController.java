package by.bsuir.news.controller;

import by.bsuir.news.dto.request.MarkerRequestTo;
import by.bsuir.news.dto.response.MarkerResponseTo;
import by.bsuir.news.entity.Marker;
import by.bsuir.news.repository.MarkerRepository;
import by.bsuir.news.service.GenericService;
import by.bsuir.news.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/markers")
public class MarkerController {
    @Autowired
    private MarkerService markerService = new MarkerService();

    @GetMapping
    public ResponseEntity getAllMarkers() {
        try {
            return new ResponseEntity(markerService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMarker(@PathVariable Long id) {
        try {
            return new ResponseEntity(markerService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }

    @PostMapping
    public ResponseEntity saveMarker(@RequestBody MarkerRequestTo marker) {
        try {
            return new ResponseEntity(markerService.create(marker), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(marker);
        }
    }

    @PutMapping("")
    public ResponseEntity updateMarker(@RequestBody MarkerRequestTo marker) {
        try {
            return new ResponseEntity(markerService.update(marker), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().header(e.getMessage()).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMarker(@PathVariable Long id) {
        try {
            return new ResponseEntity(markerService.delete(id), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
