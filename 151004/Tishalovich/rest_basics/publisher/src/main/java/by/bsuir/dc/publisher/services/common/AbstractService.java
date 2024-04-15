package by.bsuir.dc.publisher.services.common;

import by.bsuir.dc.publisher.services.exceptions.ApiException;

import java.util.List;

public interface AbstractService<I, E> {

    E create(I author) throws ApiException;

    List<E> getAll() throws ApiException;

    E get(Long id) throws ApiException;

    E update(I author) throws ApiException;

    void delete(Long id) throws ApiException;

}
