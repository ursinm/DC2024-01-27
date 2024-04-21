package by.denisova.rest.service;

import by.denisova.rest.model.Editor;

import java.util.List;

public interface EditorService {

    Editor findById(Long id);

    void deleteById(Long id);

    Editor save(Editor editor);

    Editor update(Editor editor);

    List<Editor> findAll();
}
