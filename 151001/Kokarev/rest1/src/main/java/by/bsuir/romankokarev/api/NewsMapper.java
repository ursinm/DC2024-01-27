package by.bsuir.romankokarev.api;

import by.bsuir.romankokarev.impl.bean.News;
import by.bsuir.romankokarev.impl.dto.NewsRequestTo;
import by.bsuir.romankokarev.impl.dto.NewsResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);
    NewsRequestTo NewsToNewsRequestTo(News news);
    NewsResponseTo NewsToNewsResponseTo(News news);
    News NewsResponseToToNews(NewsResponseTo responseTo);
    News NewsRequestToToNews(NewsRequestTo requestTo);
}
