package org.example.dc.controllers;

import org.example.dc.model.Editor;
import org.example.dc.model.EditorDto;
import org.example.dc.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/v1.0/editors")
public class EditorsController {
    @Autowired
    private EditorService editorService;
    @Cacheable
    @GetMapping
    public List<EditorDto> getEditors() {
        return editorService.getEditors();
    }
    @Cacheable
    @GetMapping("/{id}")
    public EditorDto getEditor(@PathVariable int id) {
        return editorService.getEditorById(id);
    }

    @PostMapping
    public EditorDto createEditor(@RequestBody @Valid EditorDto editorDto, BindingResult br, HttpServletResponse response) {
        EditorDto editor = new EditorDto();
        if(br.hasErrors()) {
            response.setStatus(400);
            return editor;
        }
        response.setStatus(201);
        try {
            editor = editorService.createEditor(editorDto);
        } catch (Exception e) {
            response.setStatus(403);
            editor = editorService.getEditors().stream()
                    .filter(ed -> ed.getLogin().equals(editorDto.getLogin()))
                    .findFirst().get();
        }
        return editor;
    }

    @PutMapping()
    public EditorDto updateEditor(@RequestBody @Valid EditorDto editorDto, BindingResult br, HttpServletResponse response) {
        if(br.hasErrors()) {
            response.setStatus(401);
            return editorService.getEditorById(editorDto.getId());
        }
        try {
            return editorService.updateEditor(editorDto);
        } catch (Exception e) {
            response.setStatus(402);
        }
        return editorService.getEditorById(editorDto.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteEditor(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            editorService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }
}