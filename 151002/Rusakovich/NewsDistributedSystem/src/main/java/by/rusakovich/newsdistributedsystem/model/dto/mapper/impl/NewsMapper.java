package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.mapper.ConversionError;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.news.NewsRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.news.NewsResponseTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper extends EntityMapper<Long, News<Long>, NewsRequestTO, NewsResponseTO> {

    @Override
    News<Long> mapToEntity(NewsRequestTO request)throws ConversionError;
    @Override
    List<NewsResponseTO> mapToResponseList(Iterable<News<Long>> entities)throws ConversionError;
    @Override
    NewsResponseTO mapToResponse(News<Long> entity)throws ConversionError;
}

