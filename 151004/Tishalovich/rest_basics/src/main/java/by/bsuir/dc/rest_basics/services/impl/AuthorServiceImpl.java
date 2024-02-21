package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.AuthorService;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl
        extends AbstractServiceImpl<AuthorRequestTo, AuthorResponseTo, Author>
        implements AuthorService {

    public AuthorServiceImpl(MemoryRepository<Author> dao,
                             EntityMapper<AuthorRequestTo, AuthorResponseTo,
                                     Author> mapper) {
        super(dao, mapper);
    }

}
