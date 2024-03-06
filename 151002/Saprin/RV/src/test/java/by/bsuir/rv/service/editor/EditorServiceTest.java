package by.bsuir.rv.service.editor;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.service.editor.impl.EditorService;
import by.bsuir.rv.util.converter.editor.EditorConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditorServiceTest {

    @Mock
    private EditorRepositoryMemory editorRepository;

    @Mock
    private EditorConverter editorConverter;

    @InjectMocks
    private EditorService editorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEditors_shouldReturnListOfEditors() {
        // Arrange
        Editor editor1 = new Editor();
        Editor editor2 = new Editor();

        List<Editor> editors = Arrays.asList(editor1, editor2);
        when(editorRepository.findAll()).thenReturn(editors);

        when(editorConverter.convertToResponse(any())).thenReturn(new EditorResponseTo());

        List<EditorResponseTo> result = editorService.getEditors();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getEditorById_shouldReturnEditorById() throws EntityNotFoundException, RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);
        Editor editor = new Editor();
        when(editorRepository.findById(editorId)).thenReturn(editor);

        when(editorConverter.convertToResponse(editor)).thenReturn(new EditorResponseTo());

        EditorResponseTo result = editorService.getEditorById(editorId);

        assertNotNull(result);
        verify(editorRepository, times(1)).findById(editorId);
        verify(editorConverter, times(1)).convertToResponse(editor);
    }

    @Test
    void createEditor_shouldSaveEditor() {
        EditorRequestTo editorRequest = new EditorRequestTo();
        Editor editor = new Editor();
        when(editorConverter.convertToEntity(editorRequest)).thenReturn(editor);
        when(editorConverter.convertToResponse(editor)).thenReturn(new EditorResponseTo());
        when(editorRepository.save(editor)).thenReturn(editor);

        EditorResponseTo result = editorService.createEditor(editorRequest);

        assertNotNull(result);
        verify(editorRepository, times(1)).save(editor);
        verify(editorConverter, times(1)).convertToResponse(editor);
    }

    @Test
    void updateEditor_shouldUpdateEditor() throws EntityNotFoundException, RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);
        EditorRequestTo editorRequest = new EditorRequestTo();
        Editor editor = new Editor();

        when(editorConverter.convertToEntity(editorRequest)).thenReturn(editor);
        when(editorConverter.convertToResponse(editor)).thenReturn(new EditorResponseTo());
        when(editorRepository.findById(editorId)).thenReturn(new Editor());
        when(editorRepository.save(editor)).thenReturn(editor);

        EditorResponseTo result = editorService.updateEditor(editorRequest);

        assertNotNull(result);
        verify(editorRepository, times(1)).save(any());
        verify(editorConverter, times(1)).convertToResponse(any());
    }

    @Test
    void deleteEditor_shouldDeleteEditor() throws RepositoryException {
        BigInteger editorId = BigInteger.valueOf(1);

        assertDoesNotThrow(() -> editorService.deleteEditor(editorId));

        verify(editorRepository, times(1)).deleteById(editorId);
    }
}
