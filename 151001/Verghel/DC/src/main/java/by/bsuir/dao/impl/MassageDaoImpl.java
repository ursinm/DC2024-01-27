package by.bsuir.dao.impl;

import by.bsuir.dao.MassageDao;
import by.bsuir.entities.Massage;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MassageDaoImpl implements MassageDao {
    private long counter = 0;
    private final Map<Long, Massage> map = new HashMap<>();

    @Override
    public Massage save(Massage entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null){
            throw new DeleteException("The Massage has not been deleted", 40003L);
        }
    }

    @Override
    public List<Massage> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Massage> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Massage update(Massage updatedMassage) throws UpdateException {
        Long id = updatedMassage.getId();

        if (map.containsKey(id)) {
            Massage existingMassage = map.get(id);
            BeanUtils.copyProperties(updatedMassage, existingMassage);
            return existingMassage;
        } else {
            throw new UpdateException("Massage update failed", 40002L);
        }
    }
}
