package by.bsuir.dao.impl;

import by.bsuir.dao.CreatorDao;
import by.bsuir.entities.Creator;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CreatorDaoImpl implements CreatorDao {

    private long counter = 0;
    private final Map<Long, Creator> map = new HashMap<>();

    @Override
    public Creator save(Creator entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Creator has not been deleted", 40003L);
        }
    }

    @Override
    public List<Creator> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Creator> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Creator update(Creator updatedCreator) throws UpdateException {
        Long id = updatedCreator.getId();

        if (map.containsKey(id)) {
            Creator existingCreator = map.get(id);
            BeanUtils.copyProperties(updatedCreator, existingCreator);
            return existingCreator;
        } else {
            throw new UpdateException("Creator update failed", 40002L);
        }
    }
}
