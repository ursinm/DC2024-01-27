package by.bsuir.newsapi.dao.impl;

import by.bsuir.newsapi.dao.common.AbstractMemoryRepository;
import by.bsuir.newsapi.model.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepository extends AbstractMemoryRepository<Tag> {

}
