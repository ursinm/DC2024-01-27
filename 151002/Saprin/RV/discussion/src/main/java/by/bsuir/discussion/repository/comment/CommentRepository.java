package by.bsuir.discussion.repository.comment;

import by.bsuir.discussion.bean.Comment;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, BigInteger> {
    @Query("SELECT * FROM tbl_comment WHERE com_id = :id ALLOW FILTERING")
    Optional<Comment> findByCom_id(@Param("id") BigInteger id);

    @Query("DELETE FROM tbl_comment WHERE com_country = :com_country AND com_id = :id")
    void deleteByCom_id(@Param("com_country") String country, @Param("id") BigInteger id);
}
