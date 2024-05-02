package com.luschickij.DC_lab.service.mapper;

import com.luschickij.DC_lab.model.entity.News;
import com.luschickij.DC_lab.model.request.NewsRequestTo;
import com.luschickij.DC_lab.model.response.NewsResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface NewsMapper {
    @Mapping(source = "creator.id", target = "creatorId")
    NewsResponseTo getResponse(News news);
    @Mapping(source = "creator.id", target = "creatorId")
    List<NewsResponseTo> getListResponse(Iterable<News> news);
    @Mapping(source = "creatorId", target = "creator", qualifiedByName = "creatorRefFromCreatorId")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "posts", ignore = true)
    News getNews(NewsRequestTo newsRequestTo);
}