package by.rusakovich.publisher.service.impl;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.dto.news.NewsRequestTO;
import by.rusakovich.publisher.model.dto.news.NewsResponseTO;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaNews;
import by.rusakovich.publisher.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends EntityService<Long, NewsRequestTO, NewsResponseTO, JpaNews> {

    public NewsService(EntityMapper<Long, JpaNews, NewsRequestTO, NewsResponseTO> mapper, IEntityRepository<Long, JpaNews> rep) {
        super(mapper, rep);
    }
}
