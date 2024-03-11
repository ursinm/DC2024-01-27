package by.bsuir.newsapi.dao.impl;

import by.bsuir.newsapi.dao.common.AbstractMemoryRepository;
import by.bsuir.newsapi.model.entity.News;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NewsRepository extends AbstractMemoryRepository<News> {
    
}
