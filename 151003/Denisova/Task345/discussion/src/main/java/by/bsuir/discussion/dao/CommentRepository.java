package by.bsuir.discussion.dao;

import by.bsuir.discussion.model.entity.Comment;
import by.bsuir.discussion.model.entity.CommentKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, CommentKey> {
    @Query(allowFiltering = true)
    Optional<Comment> findByKeyId(Long id);
}
