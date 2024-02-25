package by.bsuir.rv.service.editor.impl;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.editor.EditorRepositoryMemory;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.service.editor.IEditorService;
import by.bsuir.rv.util.converter.editor.EditorConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class EditorService implements IEditorService {

    private final EditorRepositoryMemory editorRepository;
    private final EditorConverter editorConverter;
    private final String ENTITY_NAME = "editor";

    @Autowired
    public EditorService(EditorRepositoryMemory editorRepository, EditorConverter editorConverter) {
        this.editorRepository = editorRepository;
        this.editorConverter = editorConverter;
    }


    @Override
    public List<EditorResponseTo> getEditors() {
        List<Editor> editors = editorRepository.findAll();
        return editors.stream().map(editorConverter::convertToResponse).toList();
    }

    @Override
    public EditorResponseTo getEditorById(BigInteger id) throws EntityNotFoundException {
        Editor editor;
        try {
            editor = editorRepository.findById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return editorConverter.convertToResponse(editor);
    }

    @Override
    public EditorResponseTo createEditor(EditorRequestTo editor) {
        Editor savedEditor = editorRepository.save(editorConverter.convertToEntity(editor));
        return editorConverter.convertToResponse(savedEditor);
    }

    @Override
    public EditorResponseTo updateEditor(EditorRequestTo editor) throws EntityNotFoundException {
        try {
            editorRepository.findById(editor.getId());
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, editor.getId());
        }
        Editor savedEditor = editorRepository.save(editorConverter.convertToEntity(editor));
        return editorConverter.convertToResponse(savedEditor);
    }

    @Override
    public void deleteEditor(BigInteger id) throws EntityNotFoundException {
        try {
            editorRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }
}
