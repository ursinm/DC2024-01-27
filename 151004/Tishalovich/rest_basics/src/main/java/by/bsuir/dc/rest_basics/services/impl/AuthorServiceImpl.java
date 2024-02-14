package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.AuthorDao;
import by.bsuir.dc.rest_basics.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import by.bsuir.dc.rest_basics.services.AuthorService;
import by.bsuir.dc.rest_basics.services.exceptions.AuthorSubCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public AuthorResponseTo create(AuthorRequestTo authorRequestTo) {
        Author author = AuthorMapper.INSTANCE.requestToModel(authorRequestTo);
        author = authorDao.save(author);
        return AuthorMapper.INSTANCE.modelToResponse(author);
    }

    @Override
    public List<AuthorResponseTo> getAll() {
        Iterable<Author> authors = authorDao.findAll();
        return StreamSupport
                .stream(authors.spliterator(), false)
                .map(AuthorMapper.INSTANCE::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseTo get(long id) throws ApiException {
        Optional<Author> author = authorDao.findById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    new ApiExceptionInfo(
                            HttpStatus.NOT_FOUND.value(),
                            AuthorSubCode.WRONG_ID.getSubCode(),
                            "There is no author with id = " + id));
        }

        return AuthorMapper.INSTANCE.modelToResponse(author.get());
    }

    @Override
    public AuthorResponseTo update(long id, AuthorRequestTo authorRequestTo) {
        Author author = AuthorMapper.INSTANCE
                .requestToModel(authorRequestTo);

        author = authorDao.updateById(author, id);

        return AuthorMapper.INSTANCE
                .modelToResponse(author);
    }

    @Override
    public AuthorResponseTo delete(long id) {
        return AuthorMapper.INSTANCE
                .modelToResponse(authorDao.delete(id));
    }

}
