package by.bsuir.news.repository;

import java.util.List;

public interface IMemoRepository<T> {
    public List<T> findAll();
    public T findById(Long id);
    public Long save(T entity);
    public Long delete(Long id);
    public Long update(Long id, T entity);
}
