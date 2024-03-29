package by.bsuir.newsapi.service.mapper;

import by.bsuir.newsapi.model.entity.News;
import by.bsuir.newsapi.model.entity.Tag;
import by.bsuir.newsapi.model.request.NewsRequestTo;
import by.bsuir.newsapi.model.request.TagRequestTo;
import by.bsuir.newsapi.model.response.NewsResponseTo;
import by.bsuir.newsapi.model.response.TagResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EditorMapper.class)
public interface NewsMapper {
    NewsResponseTo getResponseTo(News news);

    List<NewsResponseTo> getListResponseTo(Iterable<News> news);

    News getNews(NewsRequestTo newsRequestTo);
}
