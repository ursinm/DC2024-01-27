package com.luschickij.discussion.repository;

import com.luschickij.discussion.model.Post;
import com.luschickij.discussion.model.PostKey;
import org.hibernate.annotations.processing.HQL;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CassandraRepository<Post, PostKey> {

    List<Post> findByNewsId(Long newsId);


    // Due to this application structure we should use allow
    // filtering by id so we can connect with publisher microservice

    @Query("SELECT * FROM distcomp.tbl_post WHERE id = :id ALLOW FILTERING")
    List<Post> findById(Long id);

    void deleteByCountryAndNewsIdAndId(String country, Long newsId, Long id);

    int countByCountry(String country);

    @Query("DELETE FROM distcomp.tbl_post WHERE country = :country AND newsid = :newsId AND id = :id")
    void deleteById(String country, Long newsId, Long id);
}
