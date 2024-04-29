package by.bsuir.services;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.entities.Message;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.MessageListMapper;
import by.bsuir.mapper.MessageMapper;
import by.bsuir.repository.IssueRepository;
import by.bsuir.repository.MessageRepository;
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
    MessageMapper MessageMapper;
    @Autowired
    MessageRepository MessageDao;
    @Autowired
    MessageListMapper MessageListMapper;
    @Autowired
    IssueRepository issueRepository;

    public MessageResponseTo getMessageById(@Min(0) Long id) throws NotFoundException {
        Optional<Message> Message = MessageDao.findById(id);
        return Message.map(value -> MessageMapper.MessageToMessageResponse(value)).orElseThrow(() -> new NotFoundException("Message not found!", 40004L));
    }

    public List<MessageResponseTo> getMessages(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Message> messages = MessageDao.findAll(pageable);
        return MessageListMapper.toMessageResponseList(messages.toList());
    }

    public MessageResponseTo saveMessage(@Valid MessageRequestTo Message) {
        Message messageToSave = MessageMapper.MessageRequestToMessage(Message);
        if (Message.getIssueId()!=null) {
            messageToSave.setIssue(issueRepository.findById(Message.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L)));
        }
        return MessageMapper.MessageToMessageResponse(MessageDao.save(messageToSave));
    }

    public MessageResponseTo updateMessage(@Valid MessageRequestTo Message) throws UpdateException {
        Message messageToUpdate = MessageMapper.MessageRequestToMessage(Message);
        if (!MessageDao.existsById(Message.getId())) {
            throw new UpdateException("Message not found!", 40004L);
        } else {
            if (Message.getIssueId()!=null) {
                messageToUpdate.setIssue(issueRepository.findById(Message.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L)));
            }
            return MessageMapper.MessageToMessageResponse(MessageDao.save(messageToUpdate));
        }
    }

    public List<MessageResponseTo> getMessageByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Message> message = MessageDao.findMessagesByIssue_Id(issueId);
        if (message.isEmpty()){
            throw new NotFoundException("Message not found!", 40004L);
        }
        return MessageListMapper.toMessageResponseList(message);
    }

    public void deleteMessage(@Min(0) Long id) throws DeleteException {
        if (!MessageDao.existsById(id)) {
            throw new DeleteException("Message not found!", 40004L);
        } else {
            MessageDao.deleteById(id);
        }
    }
}
