package by.bsuir.rv.exception;

import by.bsuir.rv.repository.exception.RepositoryException;

import java.math.BigInteger;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entity, BigInteger id) {
        super(entity + " with id " + id + " not found");
    }

}
