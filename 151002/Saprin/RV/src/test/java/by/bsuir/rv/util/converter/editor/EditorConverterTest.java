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
        assertEquals(editor.getEd_id(), response.getId());
        assertEquals(editor.getEd_login(), response.getLogin());
        assertEquals(editor.getEd_password(), response.getPassword());
        assertEquals(editor.getEd_firstname(), response.getFirstname());
        assertEquals(editor.getEd_lastname(), response.getLastname());
    }

    @Test
    void convertToEntity_shouldConvertEditorRequestToEditor() {
        EditorRequestTo editorRequest = new EditorRequestTo(BigInteger.ONE, "test_login", "test_password", "John", "Doe");
        EditorConverter editorConverter = new EditorConverter();

        Editor editor = editorConverter.convertToEntity(editorRequest);

        assertNotNull(editor);
        assertEquals(editorRequest.getId(), editor.getEd_id());
        assertEquals(editorRequest.getLogin(), editor.getEd_login());
        assertEquals(editorRequest.getPassword(), editor.getEd_password());
        assertEquals(editorRequest.getFirstname(), editor.getEd_firstname());
        assertEquals(editorRequest.getLastname(), editor.getEd_lastname());
    }
}
