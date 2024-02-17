package by.bsuir.dc.rest_basics.services;

import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import java.util.List;

public interface AuthorService {

    AuthorResponseTo create(AuthorRequestTo author);

    List<AuthorResponseTo> getAll();

    AuthorResponseTo get(Long id);

    AuthorResponseTo update(AuthorRequestTo author);

    AuthorResponseTo delete(Long id);

}
