package by.bsuir.dc.rest_basics.dal.common;

import java.util.Optional;

public interface CrudRepository<T, ID> {

    Iterable<T> getAll();

    Optional<T> getById(ID id);

    Optional<T> save(T entity);

    Optional<T> update(T entity);

    Optional<T> delete(T entity);

}
