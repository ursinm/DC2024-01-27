package com.example.discussion.dao.repository;

import com.example.discussion.model.entity.Message;
import com.example.discussion.model.entity.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    @Query(allowFiltering = true)
    Optional<Message> findByKeyId(Long id);
   /* @Query("SELECT * FROM tbl_message WHERE message_country = :message_country AND message_id = :id")
    Optional<MessageKey> getById(@Param("message_country") String country, @Param("id") BigInteger id);

    @Query("DELETE FROM tbl_message WHERE message_country = :message_country AND message_id = :id")
    void removeById(@Param("message_country") String country, @Param("id") BigInteger id);

    @Query("INSERT INTO tbl_message (message_country, message_id, message_issueid, message_content) VALUES (:#{#message.message_country}, :#{#message.id}, :#{#message.issueId}, :#{#message.content})")
    void saveMessage(@Param("message") MessageKey message);

    @Query("UPDATE tbl_message SET message_content = :#{#message.content} WHERE message_country = :#{#message.message_country} AND message_id = :#{#message.id} AND message_issueid = :#{#message.issueId}")
    void updateMessage(@Param("message") MessageKey message);
*/
}
