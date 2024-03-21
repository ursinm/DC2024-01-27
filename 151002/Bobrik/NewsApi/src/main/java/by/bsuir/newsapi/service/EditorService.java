package by.bsuir.newsapi.service;

import by.bsuir.newsapi.dao.impl.EditorRepository;
import by.bsuir.newsapi.model.entity.Editor;
import by.bsuir.newsapi.model.request.EditorRequestTo;
import by.bsuir.newsapi.model.response.EditorResponseTo;
import by.bsuir.newsapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.newsapi.service.exceptions.ResourceStateException;
import by.bsuir.newsapi.service.mapper.EditorMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class EditorService implements RestService<EditorRequestTo, EditorResponseTo> {
    private final EditorRepository editorRepository;

    private final EditorMapper editorMapper;

    @Override
    public List<EditorResponseTo> findAll() {
        return editorMapper.getListResponseTo(editorRepository.getAll());
    }

    @Override
    public EditorResponseTo findById(Long id) {
        return editorMapper.getResponseTo(editorRepository
                .getBy(id)
                .orElseThrow(() -> editorNotFoundException(id)));
    }

    @Override
    public EditorResponseTo create(EditorRequestTo editorTo) {
        return editorRepository
                .save(editorMapper.getEditor(editorTo))
                .map(editorMapper::getResponseTo)
                .orElseThrow(EditorService::editorStateException);
    }

    @Override
    public EditorResponseTo update(EditorRequestTo editorTo) {
        editorRepository
                .getBy(editorMapper.getEditor(editorTo).getId())
                .orElseThrow(() -> editorNotFoundException(editorMapper.getEditor(editorTo).getId()));
        return editorRepository
                .update(editorMapper.getEditor(editorTo))
                .map(editorMapper::getResponseTo)
                .orElseThrow(EditorService::editorStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!editorRepository.removeById(id)) {
            throw editorNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException editorNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find editor with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 23);
    }

    private static ResourceStateException editorStateException() {
        return new ResourceStateException("Failed to create/update editor with specified credentials", HttpStatus.CONFLICT.value() * 100 + 24);
    }
}
