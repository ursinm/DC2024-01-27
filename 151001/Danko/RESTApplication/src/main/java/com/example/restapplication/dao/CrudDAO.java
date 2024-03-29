package com.example.restapplication.dao;

import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.UpdateException;

import java.util.List;
import java.util.Optional;

public interface CrudDAO <T>{
    T save(T entity);
    void delete(long id) throws DeleteException;
    List<T> findAll();
    Optional<T> findById(long id);
    T update(T entity) throws UpdateException;
}