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
    CreatorService creatorService;

    @GetMapping
    public ResponseEntity<List<CreatorResponseTo>> getCreators(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(creatorService.getCreators(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseTo> getCreator(@PathVariable Long id) {
        return ResponseEntity.status(200).body(creatorService.getCreatorById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreator(@PathVariable Long id) {
        creatorService.deleteCreator(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreatorResponseTo> saveCreator(@RequestBody CreatorRequestTo creator) {
        CreatorResponseTo savedCreator = creatorService.saveCreator(creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCreator);
    }

    @PutMapping
    public ResponseEntity<CreatorResponseTo> updateCreator(@RequestBody CreatorRequestTo creator) {
        return ResponseEntity.status(HttpStatus.OK).body(creatorService.updateCreator(creator));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<CreatorResponseTo> getCreatorByIssueId(@PathVariable Long id){
        return ResponseEntity.status(200).body(creatorService.getCreatorByIssueId(id));
    }
}
