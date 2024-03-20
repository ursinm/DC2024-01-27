package app.services;

import app.dao.MessageDao;
import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import app.entities.Message;
import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import app.mapper.MessageListMapper;
import app.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageListMapper messageListMapper;

    public MessageResponseTo getMessageById(@Min(0) Long id) throws NotFoundException {
        Optional<Message> message = messageDao.findById(id);
        return message.map(value -> messageMapper.messageToMessageResponse(value)).orElseThrow(() -> new NotFoundException("message not found!", 40004L));
    }

    public List<MessageResponseTo> getmessages() {
        return messageListMapper.toMessageResponseList(messageDao.findAll());
    }

    public MessageResponseTo savemessage(@Valid MessageRequestTo message) {
        Message messageToSave = messageMapper.messageRequestToMessage(message);
        return messageMapper.messageToMessageResponse(messageDao.save(messageToSave));
    }

    public void deleteMessage(@Min(0) Long id) throws DeleteException {
        messageDao.delete(id);
    }

    public MessageResponseTo updateMessage(@Valid MessageRequestTo message) throws UpdateException {
        Message messageToUpdate = messageMapper.messageRequestToMessage(message);
        return messageMapper.messageToMessageResponse(messageDao.update(messageToUpdate));
    }

    public MessageResponseTo getMessageByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        Optional<Message> message = messageDao.getMessageByTweetId(tweetId);
        return message.map(value -> messageMapper.messageToMessageResponse(value)).orElseThrow(() -> new NotFoundException("message not found!", 40004L));
    }
}
