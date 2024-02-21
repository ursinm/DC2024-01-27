package by.bsuir.rv.service.editor;

import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface IEditorService {

    List<EditorResponseTo> getEditors();
    EditorResponseTo getEditorById(BigInteger id) throws EntityNotFoundException;
    EditorResponseTo createEditor(EditorRequestTo editor);
    EditorResponseTo updateEditor(EditorRequestTo editor) throws EntityNotFoundException;
    void deleteEditor(BigInteger id) throws EntityNotFoundException;

}
