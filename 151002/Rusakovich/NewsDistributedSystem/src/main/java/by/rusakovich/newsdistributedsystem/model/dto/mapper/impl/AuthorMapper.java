package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.news.NewsRequestTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<Long, Author<Long>, AuthorRequestTO, AuthorResponseTO> {
    Author<Long> mapToEntity(NewsRequestTO request);
    List<AuthorResponseTO> mapToResponseList(Iterable<Author<Long>> entities);
    AuthorResponseTO mapToResponse(Author<Long> entity);
}
