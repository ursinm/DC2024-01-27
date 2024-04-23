package by.denisova.jpa.service;

import by.denisova.jpa.model.Editor;

import java.util.List;

public interface EditorService {

    Editor findById(Long id);

    void deleteById(Long id);

    Editor save(Editor editor);

    Editor update(Editor editor);

    List<Editor> findAll();
}
