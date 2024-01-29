package by.bsuir.controllers;

import by.bsuir.dto.EditorResponseTo;
import by.bsuir.entities.Editor;
import by.bsuir.mapper.EditorMapper;
import by.bsuir.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/editors")
public class EditorController {
    @Autowired
    EditorService editorService;
    @GetMapping
    public EditorResponseTo getEditor(){
        return editorService.editorResponseMapping(new Editor(1L, "123", "123", "aaa", "bbb"));
    }
}
