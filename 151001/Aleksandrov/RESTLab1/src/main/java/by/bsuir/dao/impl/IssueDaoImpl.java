package by.bsuir.dao.impl;

import by.bsuir.dao.IssueDao;
import by.bsuir.dao.LabelDao;
import by.bsuir.entities.Issue;
import by.bsuir.entities.Label;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IssueDaoImpl implements IssueDao {

    private long counter = 0;
    private final Map<Long, Issue> map = new HashMap<>();

    @Autowired
    LabelDao labelDao;

    @Override
    public Issue save(Issue entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The issue has not been deleted", 40003L);
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
    public Issue update(Issue updatedIssue) throws UpdateException {
        Long id = updatedIssue.getId();

        if (map.containsKey(id)) {
            Issue existingIssue = map.get(id);
            BeanUtils.copyProperties(updatedIssue, existingIssue);
            return existingIssue;
        } else {
            throw new UpdateException("Issue update failed", 40002L);
        }
    }


    @Override
    public Optional<Issue> getIssueByCriteria(String labelName, Long labelId, String title, String content) {
        for (Issue issue : map.values()) {
            if (matchesCriteria(issue, labelName, labelId, title, content)) {
                return Optional.ofNullable(issue);
            }
        }
        return Optional.empty();
    }

    private boolean matchesCriteria(Issue issue, String labelName, Long labelId, String title, String content) {
        if (labelName != null) {
            Label label = labelDao.getLabelByIssueId(issue.getId()).orElse(null);
            if (label == null || !label.getName().equals(labelName)) {
                return false;
            }
        }
        if (labelId != null) {
            if (!Objects.equals(issue.getLabelId(), labelId)) {
                return false;
            }
        }
        if (title != null) {
            if (!issue.getTitle().equals(title)) {
                return false;
            }
        }
        if (content != null) {
            return issue.getContent().equals(content);
        }
        return true;
    }
}
