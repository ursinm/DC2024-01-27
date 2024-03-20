package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.news.NewsRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.news.NewsResponseTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface NewsMapper extends EntityMapper<Long, News<Long>, NewsRequestTO, NewsResponseTO> {

    News<Long> mapToEntity(NewsRequestTO request);
    List<NewsResponseTO> mapToResponseList(Iterable<News<Long>> entities);
    NewsResponseTO mapToResponse(News<Long> entity);
}

