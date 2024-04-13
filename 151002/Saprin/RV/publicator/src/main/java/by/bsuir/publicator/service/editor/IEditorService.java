package by.bsuir.publicator.service.editor;

import by.bsuir.publicator.dto.EditorRequestTo;
import by.bsuir.publicator.dto.EditorResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface IEditorService {

    List<EditorResponseTo> getEditors();
    EditorResponseTo getEditorById(BigInteger id) throws EntityNotFoundException;
    EditorResponseTo createEditor(EditorRequestTo editor) throws DuplicateEntityException;
    EditorResponseTo updateEditor(EditorRequestTo editor) throws EntityNotFoundException;
    void deleteEditor(BigInteger id) throws EntityNotFoundException;

}
