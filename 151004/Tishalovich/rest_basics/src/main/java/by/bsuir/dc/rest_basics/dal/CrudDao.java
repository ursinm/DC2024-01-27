package by.bsuir.dc.rest_basics.dal;

import java.util.Optional;

public interface CrudDao<T> {

    T save(T entity);

    T delete(long id);

    Iterable<T> findAll();

    Optional<T> findById(long id);

    T updateById(T entity, long id);

    boolean existsById(long id);

}
