package by.bsuir.services;

import by.bsuir.dao.MessageDao;
import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.entities.Message;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.MessageListMapper;
import by.bsuir.mapper.MessageMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MessageService {

    @Autowired
    MessageMapper MessageMapper;
    @Autowired
    MessageDao MessageDao;
    @Autowired
    MessageListMapper MessageListMapper;

    public MessageResponseTo getMessageById(@Min(0) Long id) throws NotFoundException {
        Optional<Message> Message = MessageDao.findById(id);
        return Message.map(value -> MessageMapper.MessageToMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found!", 40004L));
    }

    public List<MessageResponseTo> getMessages() {
        return MessageListMapper.toMessageResponseList(MessageDao.findAll());
    }

    public MessageResponseTo saveMessage(@Valid MessageRequestTo Message) {
        Message messageToSave = MessageMapper.MessageRequestToMessage(Message);
        return MessageMapper.MessageToMessageResponse(MessageDao.save(messageToSave));
    }

    public MessageResponseTo updateMessage(@Valid MessageRequestTo Message) throws UpdateException {
        Message messageToUpdate = MessageMapper.MessageRequestToMessage(Message);
        return MessageMapper.MessageToMessageResponse(MessageDao.update(messageToUpdate));
    }

    public MessageResponseTo getMessageByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Message> Message = MessageDao.getMessageByIssueId(issueId);
        return Message.map(value -> MessageMapper.MessageToMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found!", 40004L));
    }

    public void deleteMessage(@Min(0) Long id) throws DeleteException {
        MessageDao.delete(id);
    }
}
