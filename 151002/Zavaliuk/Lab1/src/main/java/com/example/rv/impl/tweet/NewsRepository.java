package com.example.rv.impl.tweet;

import com.example.rv.api.MemRepository.MemoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsRepository extends MemoryRepository<News> {
    @Override
    public Optional<News> save(News entity) {
        entity.id = ids.incrementAndGet();
        map.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<News> update(News news) {
        Long id = news.getId();
        News memRepNews = map.get(id);

        //maybe some checks for un existing users, but how??
        if (memRepNews != null && (
                news.getEditorId() != null &&
                        news.getTitle().length() > 1 &&
                        news.getTitle().length() < 65 &&
                        news.getContent().length() > 3 &&
                        news.getContent().length() < 2049
                ) ){

        memRepNews = news;
        } else return Optional.empty();

        return Optional.of(memRepNews);
    }

    @Override
    public boolean delete(News news){
        return map.remove(news.getId(), news);
    }
}
