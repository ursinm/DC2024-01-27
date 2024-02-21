package by.bsuir.dao.impl;

import by.bsuir.dao.CommentDao;
import by.bsuir.entities.Comment;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CommentDaoImpl implements CommentDao {
    private long counter = 0;
    private final Map<Long, Comment> map = new HashMap<>();

    @Override
    public Comment save(Comment entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The comment has not been deleted", 40003L);
        }
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Comment update(Comment updatedComment) throws UpdateException {
        Long id = updatedComment.getId();

        if (map.containsKey(id)) {
            Comment existingComment = map.get(id);
            BeanUtils.copyProperties(updatedComment, existingComment);
            return existingComment;
        } else {
            throw new UpdateException("Comment update failed", 40002L);
        }
    }

    @Override
    public Optional<Comment> getCommentByIssueId(long issueId) {
        for (Comment comment : map.values()) {
            if (comment.getIssueId() != null) {
                if (comment.getIssueId() == issueId) {
                    return Optional.of(comment);
                }
            }
        }
        return Optional.empty();
    }
}
