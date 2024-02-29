package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Story;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.StoryResponseTo;
import by.bsuir.dc.rest_basics.services.StoryService;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class StoryServiceImpl extends AbstractServiceImpl<StoryRequestTo, StoryResponseTo, Story> implements StoryService {

    public StoryServiceImpl(MemoryRepository<Story> dao, EntityMapper<StoryRequestTo, StoryResponseTo, Story> mapper) {
        super(dao, mapper);
    }

    @Override
    public StoryResponseTo create(StoryRequestTo requestTo) {
        Story story = mapper.requestToModel(requestTo);

        Date curr = new Date();
        story.setCreated(curr);
        story.setModified(curr);

        Optional<Story> savingRes = dao.save(story);
        Story res= savingRes.orElseThrow();
        return mapper.modelToResponse(res);
    }

    @Override
    @SneakyThrows
    public StoryResponseTo update(StoryRequestTo requestTo) {
        Story story = mapper.requestToModel(requestTo);
        story.setModified(new Date());

        Optional<Story> updateRes = dao.update(story);
        if (updateRes.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        return mapper.modelToResponse(updateRes.get());
    }
}
