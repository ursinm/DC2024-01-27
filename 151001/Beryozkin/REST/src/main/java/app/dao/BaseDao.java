package app.dao;

import app.exceptions.DeleteException;
import app.exceptions.UpdateException;

import java.util.List;
import java.util.Optional;

public interface BaseDao <T>{
    T save(T entity);
    void delete(long id) throws DeleteException;
    List<T> findAll();
    Optional<T> findById(long id);
    T update(T entity) throws UpdateException;
}
