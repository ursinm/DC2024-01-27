package app.services;

import app.dto.MessageRequestTo;
import app.dto.MessageResponseTo;
import app.entities.Message;
import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import app.mapper.MessageListMapper;
import app.mapper.MessageMapper;
import app.repository.MessageRepository;
import app.repository.TweetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    MessageRepository messageDao;
    @Autowired
    MessageListMapper messageListMapper;
    @Autowired
    TweetRepository tweetRepository;

    public MessageResponseTo getMessageById(@Min(0) Long id) throws NotFoundException {
        Optional<Message> message = messageDao.findById(id);
        return message.map(value -> messageMapper.messageToMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found!", 40004L));
    }

    public List<MessageResponseTo> getMessages(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Message> messages = messageDao.findAll(pageable);
        return messageListMapper.toMessageResponseList(messages.toList());
    }

    public MessageResponseTo saveMessage(@Valid MessageRequestTo message) {
        Message messageToSave = messageMapper.messageRequestToMessage(message);
        if (message.getTweetId() != null) {
            messageToSave.setTweet(tweetRepository.findById(message.getTweetId()).orElseThrow(() -> new NotFoundException("Tweet not found!", 40004L)));
        }
        return messageMapper.messageToMessageResponse(messageDao.save(messageToSave));
    }

    public void deleteMessage(@Min(0) Long id) throws DeleteException {
        if (!messageDao.existsById(id)) {
            throw new DeleteException("Message not found!", 40004L);
        } else {
            messageDao.deleteById(id);
        }
    }

    public MessageResponseTo updateMessage(@Valid MessageRequestTo message) throws UpdateException {
        Message messageToUpdate = messageMapper.messageRequestToMessage(message);
        if (!messageDao.existsById(message.getId())) {
            throw new UpdateException("Message not found!", 40004L);
        } else {
            if (message.getTweetId() != null) {
                messageToUpdate.setTweet(tweetRepository.findById(message.getTweetId()).orElseThrow(() -> new NotFoundException("Tweet not found!", 40004L)));
            }
            return messageMapper.messageToMessageResponse(messageDao.save(messageToUpdate));
        }
    }

    public List<MessageResponseTo> getMessageByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        List<Message> message = messageDao.findMessagesByTweet_Id(tweetId);
        if (message.isEmpty()) {
            throw new NotFoundException("Message not found!", 40004L);
        }
        return messageListMapper.toMessageResponseList(message);
    }
}
