package by.bsuir.controllers;

import by.bsuir.dto.CreatorRequestTo;
import by.bsuir.dto.CreatorResponseTo;
import by.bsuir.services.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/creators")
public class CreatorController {
    @Autowired
    CreatorService CreatorService;

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getCreators() {
        return ResponseEntity.status(200).body(CreatorService.getCreators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> getCreator(@PathVariable Long id) {
        return ResponseEntity.status(200).body(CreatorService.getCreatorById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreator(@PathVariable Long id) {
        CreatorService.deleteCreator(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTo> saveCreator(@RequestBody CreatorRequestTo Creator) {
        CreatorResponseTo savedCreator = CreatorService.saveCreator(Creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCreator);
    }

    @PutMapping
    public ResponseEntity<CreatorResponseTo> updateCreator(@RequestBody CreatorRequestTo Creator) {
        return ResponseEntity.status(HttpStatus.OK).body(CreatorService.updateCreator(Creator));
    }
}
