package com.example.rw.repository.implementations.in_memory;

import com.example.rw.model.entity.implementations.News;
import com.example.rw.repository.interfaces.NewsRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NewsInMemoryRepository extends InMemoryRepositoryWithLongId<News> implements NewsRepository {
}
