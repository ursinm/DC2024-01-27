package by.bsuir.dao.impl;

import by.bsuir.dao.MessageDao;
import by.bsuir.entities.Message;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MessageDaoImpl implements MessageDao {
    private long counter = 0;
    private final Map<Long, Message> map = new HashMap<>();

    @Override
    public Message save(Message entity) {
        map.put(++counter, entity);
        entity.setId(counter);
        return entity;
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
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The Message has not been deleted", 40003L);
        }
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
    public Optional<Message> getMessageByIssueId(long issueId) {
        for (Message message : map.values()) {
            if (message.getIssueId() != null) {
                if (message.getIssueId() == issueId) {
                    return Optional.of(message);
                }
            }
        }
        return Optional.empty();
    }
}
