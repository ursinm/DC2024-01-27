package by.bsuir.vladislavmatsiushenko.api;

import java.util.List;

public interface InMemoryRepository<T> {
    T get(long id);
    List<T> getAll();
    T insert(T insertObject);
    boolean update(T updatingValue);
    T delete(long id);
}
