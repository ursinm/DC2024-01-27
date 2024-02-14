package by.bsuir.dc.rest_basics.services;

import by.bsuir.dc.rest_basics.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;

import java.util.List;

public interface AuthorService {

    AuthorResponseTo create(AuthorRequestTo author);

    List<AuthorResponseTo> getAll();

    AuthorResponseTo get(long id) throws ApiException;

    AuthorResponseTo update(long id, AuthorRequestTo author);

    AuthorResponseTo delete(long id);

}
