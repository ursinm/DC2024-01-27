package by.bsuir.newsapi.controller;

import by.bsuir.newsapi.model.request.EditorRequestTo;
import by.bsuir.newsapi.model.response.EditorResponseTo;
import by.bsuir.newsapi.service.EditorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
@Data
@RequiredArgsConstructor
public class EditorController {
    private final EditorService editorService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EditorResponseTo> findAll() {
        return editorService.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorResponseTo create(@Valid @RequestBody EditorRequestTo dto) {
        return editorService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo get(@Valid @PathVariable("id") Long id) {
        return editorService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo update(@Valid @RequestBody EditorRequestTo dto) {
        return editorService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        editorService.removeById(id);
    }
}
