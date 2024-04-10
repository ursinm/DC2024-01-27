package by.bsuir.romankokarev.api;

import java.util.List;

public interface InMemoryRepository<T> {
    List<T> getAll();
    T get(long id);
    T insert(T insertObject);
    boolean update(T updatingValue);
    T delete(long id);
}
