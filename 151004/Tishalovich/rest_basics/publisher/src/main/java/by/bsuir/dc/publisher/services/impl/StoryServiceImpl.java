package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.dal.StoryDao;
import by.bsuir.dc.publisher.entities.Story;
import by.bsuir.dc.publisher.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.StoryResponseTo;
import by.bsuir.dc.publisher.services.StoryService;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import by.bsuir.dc.publisher.services.exceptions.MessageSubCode;
import by.bsuir.dc.publisher.services.impl.mappers.StoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryDao dao;

    private final StoryMapper mapper;

    private final RedisStoryService redisService;

    @Override
    public StoryResponseTo create(StoryRequestTo requestTo) throws ApiException {
        Story model = mapper.requestToModel(requestTo);
        Story savingRes;
        try {
            savingRes = dao.save(model);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN.value(),
                    MessageSubCode.CONSTRAINT_VIOLATION.getSubCode(),
                    e.getMessage()
            );
        }

        StoryResponseTo res = mapper.modelToResponse(savingRes);

        redisService.save(res);

        return res;
    }

    @Override
    public List<StoryResponseTo> getAll() {
        return StreamSupport
                .stream(redisService.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public StoryResponseTo get(Long id) throws ApiException {
        Optional<StoryResponseTo> cachedStory = redisService.findById(id);
        if (cachedStory.isPresent()) {
            return cachedStory.get();
        }

        Optional<Story> author = dao.findById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        StoryResponseTo res = mapper.modelToResponse(author.get());

        redisService.save(res);

        return res;
    }

    @Override
    public StoryResponseTo update(StoryRequestTo requestTo) throws ApiException {
        Story model = mapper.requestToModel(requestTo);

        Story updateRes = dao.save(model);
        //if (updateRes == null) {
        //    throw new ApiException(
        //            HttpStatus.NOT_FOUND.value(),
        //            GeneralSubCode.WRONG_ID.getSubCode(),
        //            GeneralSubCode.WRONG_ID.getMessage()
        //    );
        //}

        StoryResponseTo res = mapper.modelToResponse(updateRes);

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
