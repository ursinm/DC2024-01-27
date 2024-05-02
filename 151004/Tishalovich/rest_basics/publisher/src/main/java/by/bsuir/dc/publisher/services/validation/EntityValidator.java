package by.bsuir.dc.publisher.services.validation;

import by.bsuir.dc.publisher.services.exceptions.ApiException;

public interface EntityValidator {

    void validateCreate(Object o) throws ApiException;

    void validateUpdate(Object o) throws ApiException;

}
