package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.AuthorService;
import by.bsuir.dc.rest_basics.services.exceptions.AuthorSubCode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final MemoryRepository<Author> authorDao;

    @Override
    public AuthorResponseTo create(AuthorRequestTo authorRequestTo) {
        Author author = AuthorMapper.INSTANCE.requestToModel(authorRequestTo);
        Optional<Author> savingRes = authorDao.save(author);
        Author savedAuthor = savingRes.orElseThrow();
        return AuthorMapper.INSTANCE.modelToResponse(savedAuthor);
    }

    @Override
    public List<AuthorResponseTo> getAll() {
        Iterable<Author> authors = authorDao.getAll();
        return StreamSupport
                .stream(authors.spliterator(), false)
                .map(AuthorMapper.INSTANCE::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows(ApiException.class)
    public AuthorResponseTo get(Long id) {
        Optional<Author> author = authorDao.getById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    AuthorSubCode.WRONG_ID.getSubCode(),
                    "There is no author with id = " + id
            );
        }

        return AuthorMapper.INSTANCE.modelToResponse(author.get());
    }

    @Override
    @SneakyThrows(ApiException.class)
    public AuthorResponseTo update(AuthorRequestTo authorRequestTo) {
        Author author = AuthorMapper.INSTANCE
                .requestToModel(authorRequestTo);

        Optional<Author> updateRes = authorDao.update(author);
        if (updateRes.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    AuthorSubCode.WRONG_ID.getSubCode(),
                    "There is no author with id = " + authorRequestTo.getId()
            );
        }

        return AuthorMapper.INSTANCE
                .modelToResponse(updateRes.get());
    }

    @Override
    @SneakyThrows(ApiException.class)
    public AuthorResponseTo delete(Long id) {
        Optional<Author> deletingRes = authorDao.delete(id);
        Author author = deletingRes.orElseThrow(
                () -> new ApiException(
                        HttpStatus.NOT_FOUND.value(),
                        AuthorSubCode.WRONG_ID.getSubCode(),
                        "There is no author with such id"
                )
        );

        return AuthorMapper.INSTANCE.modelToResponse(author);
    }

}
