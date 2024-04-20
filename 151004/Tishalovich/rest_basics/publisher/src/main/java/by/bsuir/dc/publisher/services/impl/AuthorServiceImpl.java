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
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    private final AuthorMapper mapper;

    private final RedisAuthorService redisService;

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
        AuthorResponseTo res = mapper.modelToResponse(savingRes);

        redisService.save(res);

        return res;
    }

    @Override
    public List<AuthorResponseTo> getAll() {
        return StreamSupport
                .stream(redisService.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public AuthorResponseTo get(Long id) throws ApiException {
        Optional<AuthorResponseTo> cachedAuthor = redisService.findById(id);
        if (cachedAuthor.isPresent()) {
            return cachedAuthor.get();
        }

        Optional<Author> author = dao.findById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        AuthorResponseTo res = mapper.modelToResponse(author.get());

        redisService.save(res);

        return res;
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


        AuthorResponseTo res = mapper.modelToResponse(updateRes);

        redisService.save(res);

        return res;
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

        redisService.deleteById(id);
    }

}
