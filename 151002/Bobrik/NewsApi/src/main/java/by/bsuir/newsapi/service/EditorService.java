package by.bsuir.newsapi.service;

import by.bsuir.newsapi.dao.EditorRepository;
import by.bsuir.newsapi.model.entity.Comment;
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
        return editorMapper.getListResponseTo(editorRepository.findAll());
    }

    @Override
    public EditorResponseTo findById(Long id) {
        return editorMapper.getResponseTo(editorRepository
                .findById(id)
                .orElseThrow(() -> editorNotFoundException(id)));
    }

    @Override
    public EditorResponseTo create(EditorRequestTo editorTo) {
        return editorMapper.getResponseTo(editorRepository.save(editorMapper.getEditor(editorTo)));
    }

    @Override
    public EditorResponseTo update(EditorRequestTo editorTo) {
        editorRepository
                .findById(editorMapper.getEditor(editorTo).getId())
                .orElseThrow(() -> editorNotFoundException(editorMapper.getEditor(editorTo).getId()));
        return editorMapper.getResponseTo(editorRepository.save(editorMapper.getEditor(editorTo)));
    }

    @Override
    public void removeById(Long id) {
        Editor editor = editorRepository
                .findById(id)
                .orElseThrow(() -> editorNotFoundException(id));
        editorRepository.delete(editor);
    }

    private static ResourceNotFoundException editorNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find editor with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 23);
    }
}
