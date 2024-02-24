package by.bsuir.rv.util.converter.editor;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class EditorConverterTest {

    @Test
    void convertToResponse_shouldConvertEditorToResponse() {
        Editor editor = new Editor(BigInteger.ONE, "test_login", "test_password", "John", "Doe");
        EditorConverter editorConverter = new EditorConverter();

        EditorResponseTo response = editorConverter.convertToResponse(editor);

        assertNotNull(response);
        assertEquals(editor.getId(), response.getId());
        assertEquals(editor.getLogin(), response.getLogin());
        assertEquals(editor.getPassword(), response.getPassword());
        assertEquals(editor.getFirstname(), response.getFirstname());
        assertEquals(editor.getLastname(), response.getLastname());
    }

    @Test
    void convertToEntity_shouldConvertEditorRequestToEditor() {
        EditorRequestTo editorRequest = new EditorRequestTo(BigInteger.ONE, "test_login", "test_password", "John", "Doe");
        EditorConverter editorConverter = new EditorConverter();

        Editor editor = editorConverter.convertToEntity(editorRequest);

        assertNotNull(editor);
        assertEquals(editorRequest.getId(), editor.getId());
        assertEquals(editorRequest.getLogin(), editor.getLogin());
        assertEquals(editorRequest.getPassword(), editor.getPassword());
        assertEquals(editorRequest.getFirstname(), editor.getFirstname());
        assertEquals(editorRequest.getLastname(), editor.getLastname());
    }
}
