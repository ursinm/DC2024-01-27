package by.bsuir.controllers;

import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
public class LabelController {
    @Autowired
    LabelService labelService;

    @GetMapping
    public List<LabelResponseTo> getLabels() {
        return labelService.getLabels();
    }
    @GetMapping("/{id}")
    public LabelResponseTo getLabel(@PathVariable Long id) {
        return labelService.getLabelById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id){labelService.deleteLabel(id);}

    @PostMapping
    public LabelResponseTo saveLabel(@RequestBody LabelRequestTo label){
        return labelService.saveLabel(label);
    }

    @PostMapping("/{id}")
    public LabelResponseTo updateLabel(@RequestBody LabelRequestTo label, @PathVariable Long id){
        return labelService.updateLabel(label, id);
    }
}