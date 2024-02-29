package by.bsuir.dc.rest_basics.services;

import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.services.common.AbstractService;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;

public interface AuthorService
        extends AbstractService<AuthorRequestTo, AuthorResponseTo> {

    AuthorResponseTo getAuthor(Long storyId) throws ApiException;

}
