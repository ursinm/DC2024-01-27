package by.bsuir.dao.impl;

import by.bsuir.dao.LabelDao;
import by.bsuir.entities.Comment;
import by.bsuir.entities.Label;
import by.bsuir.exceptions.comment.CommentDeleteException;
import by.bsuir.exceptions.editor.EditorUpdateException;
import by.bsuir.exceptions.label.LabelDeleteException;
import by.bsuir.exceptions.label.LabelUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LabelDaoImpl implements LabelDao {

    private long counter = 0;
    private final Map<Long, Label> map = new HashMap<>();
    @Override
    public Label save(Label entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }
    @Override
    public void delete(long id) throws LabelDeleteException {
        if (map.remove(id) == null){
            throw new LabelDeleteException("The editor has not been deleted", 400);
        }
    }

    @Override
    public List<Label> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Label> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Label update(Label updatedLabel) throws LabelUpdateException {
        Long id = updatedLabel.getId();

        if (map.containsKey(id)) {
            Label existingLabel = map.get(id);
            BeanUtils.copyProperties(updatedLabel, existingLabel);
            return existingLabel;
        } else {
            throw new LabelUpdateException("Update failed", 400);
        }
    }
}
