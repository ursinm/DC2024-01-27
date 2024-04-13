package by.bsuir.vladislavmatsiushenko.api;

import java.util.List;

public interface Service<T, K> {
    T get(long id);
    List<T> getAll();
    T add(K requestTo);
    T update(K requestTo);
    T delete(long id);
}

