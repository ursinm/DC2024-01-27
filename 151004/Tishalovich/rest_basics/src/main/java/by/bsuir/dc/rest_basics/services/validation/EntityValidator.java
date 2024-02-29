package by.bsuir.dc.rest_basics.services.validation;

import by.bsuir.dc.rest_basics.services.exceptions.ApiException;

public interface EntityValidator {

    void validateCreate(Object o) throws ApiException;

    void validateUpdate(Object o) throws ApiException;

}
