package by.rusakovich.publisher.service.impl;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.author.AuthorRequestTO;
import by.rusakovich.publisher.model.dto.author.AuthorResponseTO;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaAuthor;
import by.rusakovich.publisher.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends EntityService<Long, AuthorRequestTO, AuthorResponseTO, JpaAuthor> {
    public AuthorService(EntityMapper<Long, JpaAuthor, AuthorRequestTO, AuthorResponseTO> mapper, IEntityRepository<Long, JpaAuthor> rep) {
        super(mapper, rep);
    }
}
