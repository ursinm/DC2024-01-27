package by.bsuir.dao.impl;

import by.bsuir.dao.IssueDao;
import by.bsuir.entities.Comment;
import by.bsuir.entities.Issue;
import by.bsuir.exceptions.editor.EditorDeleteException;
import by.bsuir.exceptions.editor.EditorUpdateException;
import by.bsuir.exceptions.issue.IssueDeleteException;
import by.bsuir.exceptions.issue.IssueUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IssueDaoImpl implements IssueDao {

    private long counter = 0;
    private final Map<Long, Issue> map = new HashMap<>();
    @Override
    public Issue save(Issue entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws IssueDeleteException{
        if (map.remove(id) == null){
            throw new IssueDeleteException("The editor has not been deleted", 400);
        }
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Issue> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Issue update(Issue updatedIssue) throws IssueUpdateException {
        Long id = updatedIssue.getId();

        if (map.containsKey(id)) {
            Issue existingIssue = map.get(id);
            BeanUtils.copyProperties(updatedIssue, existingIssue);
            return existingIssue;
        } else {
            throw new IssueUpdateException("Update failed", 400);
        }
    }
}
