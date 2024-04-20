package com.example.rv.api.Controllers;

import com.example.rv.impl.editor.Editor;
import com.example.rv.impl.editor.EditorRequestTo;
import com.example.rv.impl.editor.EditorResponseTo;
import com.example.rv.impl.editor.EditorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class EditorController {

    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }


    @RequestMapping(value = "/editors", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<EditorResponseTo> getEditors() {
        return editorService.editorMapper.editorToResponseTo(editorService.editorCrudRepository.getAll());
    }

    @RequestMapping(value = "/editors", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    EditorResponseTo makeEditor(@RequestBody EditorRequestTo editorRequestTo) {

        var toBack = editorService.editorCrudRepository.save(
                editorService.editorMapper.dtoToEntity(editorRequestTo)
        );

        Editor editor = toBack.orElse(null);

        assert editor != null;
        return editorService.editorMapper.editorToResponseTo(editor);
    }

    @RequestMapping(value = "/editors/{id}", method = RequestMethod.GET)
    EditorResponseTo getEditor(@PathVariable Long id) {
        return editorService.editorMapper.editorToResponseTo(
                Objects.requireNonNull(editorService.editorCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/editors", method = RequestMethod.PUT)
    EditorResponseTo updateEditor(@RequestBody EditorRequestTo editorRequestTo, HttpServletResponse response) {
        Editor editor = editorService.editorMapper.dtoToEntity(editorRequestTo);
        var newEditor = editorService.editorCrudRepository.update(editor).orElse(null);
        if (newEditor != null) {
            response.setStatus(200);
            return editorService.editorMapper.editorToResponseTo(newEditor);
        } else{
            response.setStatus(403);
            return editorService.editorMapper.editorToResponseTo(editor);
        }
    }

    @RequestMapping(value = "/editors/{id}", method = RequestMethod.DELETE)
    int deleteEditor(@PathVariable Long id, HttpServletResponse response) {
        Editor edToDelete = editorService.editorCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(edToDelete)) {
            response.setStatus(403);
        } else {
            editorService.editorCrudRepository.delete(edToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
