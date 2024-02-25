package by.bsuir.rv.controller;

import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.service.editor.IEditorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditorControllerTest {

    @Mock
    private IEditorService editorService;

    @InjectMocks
    private EditorController editorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetEditors() {
        List<EditorResponseTo> editors = new ArrayList<>();
        when(editorService.getEditors()).thenReturn(editors);

        List<EditorResponseTo> result = editorController.getEditors();

        assertEquals(editors, result);
        verify(editorService, times(1)).getEditors();
    }

    @Test
    void testGetEditorById() throws EntityNotFoundException {
        BigInteger editorId = BigInteger.valueOf(1);
        EditorResponseTo editor = new EditorResponseTo();
        when(editorService.getEditorById(editorId)).thenReturn(editor);

        EditorResponseTo result = editorController.getEditorById(editorId);

        assertEquals(editor, result);
        verify(editorService, times(1)).getEditorById(editorId);
    }

    @Test
    void testCreateEditor() {
        EditorRequestTo editorRequest = new EditorRequestTo();
        EditorResponseTo createdEditor = new EditorResponseTo();
        when(editorService.createEditor(any())).thenReturn(createdEditor);

        EditorResponseTo result = editorController.createEditor(editorRequest);

        assertEquals(createdEditor, result);
        verify(editorService, times(1)).createEditor(editorRequest);
    }

    @Test
    void testUpdateEditor() throws EntityNotFoundException {
        EditorRequestTo updatedEditor = new EditorRequestTo();
        EditorResponseTo updatedResponse = new EditorResponseTo();
        when(editorService.updateEditor(updatedEditor)).thenReturn(updatedResponse);

        EditorResponseTo result = editorController.updateEditor(updatedEditor);

        assertEquals(updatedResponse, result);
        verify(editorService, times(1)).updateEditor(updatedEditor);
    }

    @Test
    void testDeleteEditor() throws EntityNotFoundException {
        BigInteger editorId = BigInteger.valueOf(1);

        editorController.deleteEditor(editorId);

        verify(editorService, times(1)).deleteEditor(editorId);
    }
}
