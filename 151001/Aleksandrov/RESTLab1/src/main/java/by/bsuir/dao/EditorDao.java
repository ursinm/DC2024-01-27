package by.bsuir.dao;

import by.bsuir.entities.Editor;

import java.util.List;

public interface EditorDao {
    Editor createEditor(String login, String password, String firstname, String lastname);

    List<Editor> getEditors();

    Editor getEditorById(Long Id);

    Editor updateEditor(Long id, Editor newEditor);

    void deleteEditor(Long id);

    Editor getEditorByIssueId(Long issueId);
}
