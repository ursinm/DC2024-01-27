package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Story;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.StoryResponseTo;
import by.bsuir.dc.rest_basics.services.StoryService;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import org.springframework.stereotype.Service;

@Service
public class StoryServiceImpl extends AbstractServiceImpl<StoryRequestTo, StoryResponseTo, Story> implements StoryService {

    public StoryServiceImpl(MemoryRepository<Story> dao, EntityMapper<StoryRequestTo, StoryResponseTo, Story> mapper) {
        super(dao, mapper);
    }

}
