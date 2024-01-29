package by.bsuir.controllers;

import by.bsuir.dto.EditorResponseTo;
import by.bsuir.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/editors")
public class EditorController {
    @Autowired
    EditorService editorService;

    @GetMapping
    public List<EditorResponseTo> getEditors() {
        return editorService.getEditors();
    }
    @GetMapping("/{id}")
    public EditorResponseTo getEditor(@PathVariable Long id) {
        return editorService.getEditorById(id);
    }
}
