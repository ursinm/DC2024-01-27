package org.example.discussion.impl.post;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PostRepository extends CassandraRepository<Post, BigInteger> {
    @Query("SELECT * FROM tbl_post WHERE post_country = :post_country AND post_id = :id")
    Optional<Post> findBy_id(@Param("post_country") String country, @Param("id") BigInteger id);

    @Query("DELETE FROM tbl_post WHERE post_country = :post_country AND post_id = :id")
    void deleteBy_id(@Param("post_country") String country, @Param("id") BigInteger id);
}