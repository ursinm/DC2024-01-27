package by.bsuir.romankokarev.impl.service;

import by.bsuir.romankokarev.api.Service;
import by.bsuir.romankokarev.impl.bean.News;
import by.bsuir.romankokarev.api.NewsMapper;
import by.bsuir.romankokarev.impl.dto.NewsRequestTo;
import by.bsuir.romankokarev.impl.dto.NewsResponseTo;
import by.bsuir.romankokarev.impl.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsService implements Service<NewsResponseTo, NewsRequestTo> {
    @Autowired
    private NewsRepository newsRepository;

    public NewsService() {

    }

    public List<NewsResponseTo> getAll() {
        List<News> newsList = newsRepository.getAll();
        List<NewsResponseTo> resultList = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            resultList.add(NewsMapper.INSTANCE.NewsToNewsResponseTo(newsList.get(i)));
        }
        return resultList;
    }

    public NewsResponseTo update(NewsRequestTo updatingNews) {
        News news = NewsMapper.INSTANCE.NewsRequestToToNews(updatingNews);
        if (validateNews(news)) {
            boolean result = newsRepository.update(news);
            NewsResponseTo responseTo = result ? NewsMapper.INSTANCE.NewsToNewsResponseTo(news) : null;
            return responseTo;
        }
        return new NewsResponseTo();
    }

    public NewsResponseTo get(long id) {
        return NewsMapper.INSTANCE.NewsToNewsResponseTo(newsRepository.get(id));
    }

    public NewsResponseTo delete(long id) {
        return NewsMapper.INSTANCE.NewsToNewsResponseTo(newsRepository.delete(id));
    }

    public NewsResponseTo add(NewsRequestTo newsRequestTo) {
        News news = NewsMapper.INSTANCE.NewsRequestToToNews(newsRequestTo);
        return NewsMapper.INSTANCE.NewsToNewsResponseTo(newsRepository.insert(news));
    }

    private boolean validateNews(News news) {
        String title = news.getTitle();
        String content = news.getContent();

        if ((content.length() >= 4 && content.length() <= 2048) && (title.length() >= 2 && title.length() <= 64)) {
            return true;
        }
        return false;
    }
}
