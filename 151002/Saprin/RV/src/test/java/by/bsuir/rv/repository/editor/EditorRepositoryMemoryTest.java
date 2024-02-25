package by.bsuir.rv.repository.editor;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EditorRepositoryMemoryTest {

    @InjectMocks
    private EditorRepositoryMemory editorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveEditor() {
        Editor editor = new Editor();

        Editor savedEditor = editorRepository.save(editor);

        assertNotNull(savedEditor.getId());
        assertEquals(1, editorRepository.findAll().size());
    }

    @Test
    void findAll_shouldReturnAllEditors() {
        Editor editor1 = new Editor();
        Editor editor2 = new Editor();
        editorRepository.save(editor1);
        editorRepository.save(editor2);

        List<Editor> editors = editorRepository.findAll();

        assertEquals(2, editors.size());
    }

    @Test
    void findById_shouldReturnEditorById() throws RepositoryException {
        Editor editor = new Editor();
        Editor savedEditor = editorRepository.save(editor);

        Editor foundEditor = editorRepository.findById(savedEditor.getId());

        assertNotNull(foundEditor);
        assertEquals(savedEditor.getId(), foundEditor.getId());
    }

    @Test
    void findById_shouldThrowExceptionIfEditorNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> editorRepository.findById(nonExistingId));
    }

    @Test
    void findAllById_shouldReturnEditorsByIds() throws RepositoryException {
        Editor editor1 = new Editor();
        Editor editor2 = new Editor();
        Editor savedEditor1 = editorRepository.save(editor1);
        Editor savedEditor2 = editorRepository.save(editor2);

        List<BigInteger> idsToFind = Arrays.asList(savedEditor1.getId(), savedEditor2.getId());

        List<Editor> foundEditors = editorRepository.findAllById(idsToFind);

        assertEquals(2, foundEditors.size());
    }

    @Test
    void findAllById_shouldThrowExceptionIfEditorsNotFound() {
        BigInteger nonExistingId1 = BigInteger.valueOf(999);
        BigInteger nonExistingId2 = BigInteger.valueOf(1000);
        List<BigInteger> nonExistingIds = Arrays.asList(nonExistingId1, nonExistingId2);

        assertThrows(RepositoryException.class, () -> editorRepository.findAllById(nonExistingIds));
    }

    @Test
    void deleteById_shouldDeleteEditorById() throws RepositoryException {
        Editor editor = new Editor();
        Editor savedEditor = editorRepository.save(editor);

        editorRepository.deleteById(savedEditor.getId());

        assertEquals(0, editorRepository.findAll().size());
    }

    @Test
    void deleteById_shouldThrowExceptionIfEditorNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> editorRepository.deleteById(nonExistingId));
    }
}
