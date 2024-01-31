package by.bsuir.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao <T>{
    T save(T entity);
    void delete(long id);
    List<T> findAll();
    Optional<T> findById(long id);
    T update(T entity, long id);
}
