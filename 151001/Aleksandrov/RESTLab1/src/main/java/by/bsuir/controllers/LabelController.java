package by.bsuir.controllers;

import by.bsuir.dto.EditorResponseTo;
import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.exceptions.editor.EditorDeleteException;
import by.bsuir.exceptions.editor.EditorUpdateException;
import by.bsuir.exceptions.label.LabelDeleteException;
import by.bsuir.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
public class LabelController {
    @Autowired
    LabelService labelService;

    @GetMapping
    public ResponseEntity<List<LabelResponseTo>> getLabels() {
        return ResponseEntity.status(200).body(labelService.getLabels());
    }
    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseTo> getLabel(@PathVariable Long id) {
        return ResponseEntity.status(200).body(labelService.getLabelById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id){
        try {
            labelService.deleteLabel(id);
        } catch (LabelDeleteException exception){
            return ResponseEntity.status(exception.getStatus()).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<LabelResponseTo> saveLabel(@RequestBody LabelRequestTo label){
        LabelResponseTo savedLabel = labelService.saveLabel(label);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLabel);
    }

    @PutMapping()
    public ResponseEntity<LabelResponseTo> updateLabel(@RequestBody LabelRequestTo label){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(labelService.updateLabel(label));
        } catch (EditorUpdateException exception){
            return ResponseEntity.status(exception.getStatus()).build();
        }
    }
}