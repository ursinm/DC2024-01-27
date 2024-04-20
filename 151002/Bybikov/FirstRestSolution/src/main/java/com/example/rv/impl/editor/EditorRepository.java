package com.example.rv.impl.editor;

import com.example.rv.api.MemRepository.MemoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EditorRepository extends MemoryRepository<Editor> {

    @Override
    public Optional<Editor> save(Editor entity) {
        entity.id = ids.incrementAndGet();
        map.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Editor> update(Editor editor) {
        Long id = editor.getId();
        Editor memRepEditor = map.get(id);

        if (editor.getLogin().length() > 1 && editor.getLogin().length() < 65 &&
                editor.getPassword().length() > 7 && editor.getPassword().length() < 129 &&
                editor.getFirstname().length() > 1 && editor.getFirstname().length() < 65 &&
                editor.getLastname().length() > 1 && editor.getLastname().length() < 65) {

            memRepEditor.setLogin(editor.getLogin());
            memRepEditor.setFirstname(editor.getFirstname());
            memRepEditor.setLastname(editor.getLastname());
            memRepEditor.setPassword(editor.getPassword());
        } else return Optional.empty();

        return Optional.of(memRepEditor);
    }

    @Override
    public boolean delete(Editor entity) {
        return map.remove(entity.getId(), entity);
    }
}
