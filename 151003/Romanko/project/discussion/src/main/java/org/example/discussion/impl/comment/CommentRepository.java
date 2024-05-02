package org.example.discussion.impl.comment;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, BigInteger> {
    @Query("SELECT * FROM tbl_comment WHERE comment_country = :comment_country AND comment_id = :id")
    Optional<Comment> findBy_id(@Param("comment_country") String country, @Param("id") BigInteger id);

    @Query("DELETE FROM tbl_comment WHERE comment_country = :comment_country AND comment_id = :id")
    void deleteBy_id(@Param("comment_country") String country, @Param("id") BigInteger id);
}