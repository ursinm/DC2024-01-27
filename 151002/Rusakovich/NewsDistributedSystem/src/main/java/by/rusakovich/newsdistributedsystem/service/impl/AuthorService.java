package by.rusakovich.newsdistributedsystem.service.impl;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Author;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaAuthor;
import by.rusakovich.newsdistributedsystem.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends EntityService<Long, AuthorRequestTO, AuthorResponseTO, JpaAuthor> {
    public AuthorService(EntityMapper<Long, JpaAuthor, AuthorRequestTO, AuthorResponseTO> mapper, IEntityRepository<Long, JpaAuthor> rep) {
        super(mapper, rep);
    }
}
