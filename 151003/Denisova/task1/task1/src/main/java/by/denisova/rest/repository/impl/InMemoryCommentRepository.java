package by.denisova.rest.repository.impl;

import by.denisova.rest.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCommentRepository extends AbstractInMemoryRepository<Comment> {
}
