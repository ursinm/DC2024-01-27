package by.bsuir.rv.controller;

import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import by.bsuir.rv.exception.DuplicateEntityException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.service.editor.IEditorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/editors")
public class EditorController {

    private final IEditorService editorService;

    public EditorController(IEditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EditorResponseTo> getEditors() {
        return editorService.getEditors();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo getEditorById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return editorService.getEditorById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EditorResponseTo createEditor(@Valid @RequestBody EditorRequestTo editor) throws DuplicateEntityException {
        return editorService.createEditor(editor);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo updateEditor(@Valid @RequestBody EditorRequestTo editor) throws EntityNotFoundException {
        return editorService.updateEditor(editor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEditor(@PathVariable BigInteger id) throws EntityNotFoundException {
        editorService.deleteEditor(id);
    }
}
