package by.bsuir.dao.impl;

import by.bsuir.dao.LabelDao;
import by.bsuir.entities.Label;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
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
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The label has not been deleted", 40003L);
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
    public Label update(Label updatedLabel) throws UpdateException {
        Long id = updatedLabel.getId();

        if (map.containsKey(id)) {
            Label existingLabel = map.get(id);
            BeanUtils.copyProperties(updatedLabel, existingLabel);
            return existingLabel;
        } else {
            throw new UpdateException("Label update failed", 40002L);
        }
    }

    @Override
    public Optional<Label> getLabelByIssueId(long issueId) {
        for (Label label : map.values()) {
            if (label.getIssueId() != null) {
                if (label.getIssueId() == issueId) {
                    return Optional.of(label);
                }
            }
        }
        return Optional.empty();
    }
}
