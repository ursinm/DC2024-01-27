package by.bsuir.newsapi.dao.common;

import java.util.Optional;

public interface CrudRepository<ID, T> {
    Optional<T> getBy(ID id);
    
    Iterable<T> getAll();
    
    Optional<T> save(T entity);
    
    Optional<T> update(T entity);
    
    boolean remove(T entity);

    boolean removeById(ID id);
}
