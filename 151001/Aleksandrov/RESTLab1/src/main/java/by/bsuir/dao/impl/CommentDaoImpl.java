package by.bsuir.dao.impl;

import by.bsuir.dao.CommentDao;
import by.bsuir.entities.Comment;
import by.bsuir.exceptions.comment.CommentDeleteException;
import by.bsuir.exceptions.comment.CommentUpdateException;
import by.bsuir.exceptions.editor.EditorDeleteException;
import by.bsuir.exceptions.editor.EditorUpdateException;
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
    public void delete(long id) throws CommentDeleteException {
        if (map.remove(id) == null){
            throw new CommentDeleteException("The editor has not been deleted", 400);
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
    public Comment update(Comment updatedComment) throws CommentUpdateException {
        Long id = updatedComment.getId();

        if (map.containsKey(id)) {
            Comment existingComment = map.get(id);
            BeanUtils.copyProperties(updatedComment, existingComment);
            return existingComment;
        } else {
            throw new CommentUpdateException("Update failed", 400);
        }
    }
}
