package by.bsuir.discussion.service;

import java.util.List;

public interface RestService<A, B> {
    List<B> findAll();

    B findById(Long id);

    B create(A dto);

    B update(A dto);

    void removeById(Long id);
}
