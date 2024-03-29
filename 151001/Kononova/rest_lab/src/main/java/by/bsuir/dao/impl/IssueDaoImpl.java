package by.bsuir.dao.impl;

import by.bsuir.dao.IssueDao;
import by.bsuir.dao.StickerDao;
import by.bsuir.entities.Issue;
import by.bsuir.entities.Sticker;
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
    StickerDao stickerDao;

    @Override
    public Issue save(Issue entity) {
        map.put(++counter, entity);
        entity.setId(counter);
        return entity;
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
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The issue has not been deleted", 40003L);
        }
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
    public Optional<Issue> getIssueByCriteria(String stickerName, Long stickerId, String title, String content) {
        for (Issue issue : map.values()) {
            if (matchesCriteria(issue, stickerName, stickerId, title, content)) {
                return Optional.ofNullable(issue);
            }
        }
        return Optional.empty();
    }

    private boolean matchesCriteria(Issue issue, String stickerName, Long stickerId, String title, String content) {
        if (stickerName != null) {
            Sticker sticker = stickerDao.getStickerByIssueId(issue.getId()).orElse(null);
            if (sticker == null || !sticker.getName().equals(stickerName)) {
                return false;
            }
        }
        if (stickerId != null) {
            if (!Objects.equals(issue.getStickerId(), stickerId)) {
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
