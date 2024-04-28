package by.bsuir.restapi.repository.base;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    void delete(T entity);

}
