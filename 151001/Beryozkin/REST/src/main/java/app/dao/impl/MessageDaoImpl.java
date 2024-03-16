package app.dao.impl;

import app.dao.MessageDao;
import app.exceptions.DeleteException;
import app.entities.Message;
import app.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MessageDaoImpl implements MessageDao {
    private long counter = 0;
    private final Map<Long, Message> map = new HashMap<>();

    @Override
    public Message save(Message entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Message has not been deleted", 40003L);
        }
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Message> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Message update(Message updatedMessage) throws UpdateException {
        Long id = updatedMessage.getId();

        if (map.containsKey(id)) {
            Message existingMessage = map.get(id);
            BeanUtils.copyProperties(updatedMessage, existingMessage);
            return existingMessage;
        } else {
            throw new UpdateException("Message update failed", 40002L);
        }
    }

    @Override
    public Optional<Message> getMessageByTweetId(long tweetId) {
        for (Message Message : map.values()) {
            if (Message.getTweetId() != null) {
                if (Message.getTweetId() == tweetId) {
                    return Optional.of(Message);
                }
            }
        }
        return Optional.empty();
    }
}
