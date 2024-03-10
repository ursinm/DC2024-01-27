package by.bsuir.newsapi.dao.impl;

import by.bsuir.newsapi.dao.common.AbstractMemoryRepository;
import by.bsuir.newsapi.model.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CommentRepository extends AbstractMemoryRepository<Comment> {

}
