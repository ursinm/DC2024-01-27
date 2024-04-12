package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.AuthorDao;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.MessageSubCode;
import by.bsuir.dc.publisher.services.impl.mappers.AuthorMapper;
import by.bsuir.dc.publisher.entities.Author;
import by.bsuir.dc.publisher.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.publisher.services.AuthorService;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    private final AuthorMapper mapper;

    @Override
    public AuthorResponseTo create(AuthorRequestTo requestTo) throws ApiException {
        Author model = mapper.requestToModel(requestTo);
        Author savingRes;
        try {
            savingRes = dao.save(model);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN.value(),
                    MessageSubCode.CONSTRAINT_VIOLATION.getSubCode(),
                    e.getMessage()
            );
        }
        return mapper.modelToResponse(savingRes);
    }

    @Override
    public List<AuthorResponseTo> getAll() {
        Iterable<Author> models = dao.findAll();
        return StreamSupport
                .stream(models.spliterator(), false)
                .map(mapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseTo get(Long id) throws ApiException {
        Optional<Author> author = dao.findById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        return mapper.modelToResponse(author.get());
    }

    @Override
    public AuthorResponseTo update(AuthorRequestTo requestTo) throws ApiException {
        Author model = mapper.requestToModel(requestTo);

        Author updateRes = dao.save(model);
        //if (updateRes == null) {
        //    throw new ApiException(
        //            HttpStatus.NOT_FOUND.value(),
        //            GeneralSubCode.WRONG_ID.getSubCode(),
        //            GeneralSubCode.WRONG_ID.getMessage()
        //    );
        //}

        return mapper.modelToResponse(updateRes);
    }

    @Override
    public void delete(Long id) throws ApiException {
        if (!dao.existsById(id)) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        dao.deleteById(id);
    }

}
