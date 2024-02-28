package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.dal.impl.StoryDao;
import by.bsuir.dc.rest_basics.entities.Story;
import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.AuthorService;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.AuthorSubCode;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthorServiceImpl
        extends AbstractServiceImpl<AuthorRequestTo, AuthorResponseTo, Author>
        implements AuthorService {

    protected final StoryDao storyDao;

    public AuthorServiceImpl(MemoryRepository<Author> dao,
                             EntityMapper<AuthorRequestTo, AuthorResponseTo,
                                     Author> mapper, StoryDao storyDao) {
        super(dao, mapper);
        this.storyDao = storyDao;
    }

    @Override
    public AuthorResponseTo getAuthor(Long storyId) throws ApiException {
        Optional<Story> storyRes = storyDao.getById(storyId);
        Story story = storyRes.orElseThrow(
                () -> new ApiException(
                        HttpStatus.NOT_FOUND.value(),
                        GeneralSubCode.WRONG_ID.getSubCode(),
                        GeneralSubCode.WRONG_ID.getMessage()
                )
        );

        Optional<Author> authorRes = dao.getById(story.getAuthorId());

        Author author = authorRes.orElseThrow(
                () -> new ApiException(
                        HttpStatus.NOT_FOUND.value(),
                        AuthorSubCode.NO_AUTHOR_FOR_STORY.getSubCode(),
                        AuthorSubCode.NO_AUTHOR_FOR_STORY.getMessage()
                )
        );

        return mapper.modelToResponse(author);
    }
}
