package by.bsuir.dc.repository.common;

import java.util.Optional;

public interface CrudRepository<T, ID>  {
    <S extends T> S save(S entity);

    Optional<T> getById(ID id);

    Iterable<T> getAll();

    void deleteById(ID id);

    void delete(T entity);
}