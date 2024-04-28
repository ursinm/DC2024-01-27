package by.denisova.rest.service.impl;

import by.denisova.rest.service.EditorService;
import by.denisova.rest.repository.AbstractRepository;
import by.denisova.rest.exception.EntityNotFoundException;
import by.denisova.rest.model.Editor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EditorServiceImpl implements EditorService {

    public static final String EDITOR_NOT_FOUND_MESSAGE = "Editor with id '%d' doesn't exist";
    private final AbstractRepository<Editor, Long> editorRepository;

    @Override
    public Editor findById(Long id) {
        return editorRepository.findById(id).orElseThrow(() -> {
            final String message = EDITOR_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        editorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(EDITOR_NOT_FOUND_MESSAGE));

        editorRepository.deleteById(id);
    }

    @Override
    public Editor save(Editor editor) {
        return editorRepository.save(editor);
    }

    @Override
    public Editor update(Editor editor) {
        editorRepository.findById(editor.getId()).orElseThrow(()-> new EntityNotFoundException(EDITOR_NOT_FOUND_MESSAGE));

        return editorRepository.update(editor);
    }

    @Override
    public List<Editor> findAll() {
        return editorRepository.findAll();
    }
}
