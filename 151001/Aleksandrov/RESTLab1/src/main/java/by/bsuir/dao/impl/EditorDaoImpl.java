package by.bsuir.dao.impl;

import by.bsuir.dao.EditorDao;
import by.bsuir.entities.Editor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class EditorDaoImpl implements EditorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Editor createEditor(String login, String password, String firstname, String lastname) {
        return null;
    }

    @Override
    public List<Editor> getEditors() {
        String query = "SELECT e FROM Editor e";
        return entityManager.createQuery(query, Editor.class).getResultList();
    }

    @Override
    public Editor getEditorById(Long id) {
        return entityManager.find(Editor.class, id);
    }

    @Override
    public Editor updateEditor(Long id, Editor newEditor) {
        return null;
    }

    @Override
    public void deleteEditor(Long id) {

    }

    @Override
    public Editor getEditorByIssueId(Long issueId) {
        return null;
    }
}
