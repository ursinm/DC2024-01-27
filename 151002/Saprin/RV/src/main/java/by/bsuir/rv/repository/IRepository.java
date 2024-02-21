package by.bsuir.rv.repository;

import by.bsuir.rv.bean.IdentifiedBean;
import by.bsuir.rv.repository.exception.RepositoryException;

import java.math.BigInteger;
import java.util.List;

public interface IRepository<T extends IdentifiedBean> {

    T save(T entity);

    List<T> findAll();

    T findById(BigInteger id) throws RepositoryException;

    List<T> findAllById(List<BigInteger> ids) throws RepositoryException;

    void deleteById(BigInteger id) throws RepositoryException;
}
