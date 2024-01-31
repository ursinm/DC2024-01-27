package by.bsuir.controllers;

import by.bsuir.dto.EditorRequestTo;
import by.bsuir.dto.EditorResponseTo;
import by.bsuir.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public void deleteEditor(@PathVariable Long id){editorService.deleteEditor(id);}

    @PostMapping
    public EditorResponseTo saveEditor(@RequestBody EditorRequestTo editor){
        return editorService.saveEditor(editor);
    }

    @PostMapping("/{id}")
    public EditorResponseTo updateEditor(@RequestBody EditorRequestTo editor, @PathVariable Long id){
        return editorService.updateEditor(editor, id);
    }
}
