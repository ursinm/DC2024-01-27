package by.bsuir.dao;

import by.bsuir.entities.Editor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EditorDao {
    List<Editor> getEditors();

    Editor getEditorById(Long Id);

    Editor updateEditor(Long id, Editor newEditor);

    void deleteEditor(Long id);

    Editor getEditorByIssueId(Long issueId);
}
