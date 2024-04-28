package by.bsuir.vladislavmatsiushenko.service;

import by.bsuir.vladislavmatsiushenko.mapper.MessageMapper;
import by.bsuir.vladislavmatsiushenko.dto.MessageRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.MessageResponseTo;
import by.bsuir.vladislavmatsiushenko.exception.NotFoundException;
import by.bsuir.vladislavmatsiushenko.mapper.MessageListMapper;
import by.bsuir.vladislavmatsiushenko.model.Message;
import by.bsuir.vladislavmatsiushenko.repository.IssueRepository;
import by.bsuir.vladislavmatsiushenko.repository.MessageRepository;
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

@Service
@Validated
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageListMapper messageListMapper;

    public MessageResponseTo create(@Valid MessageRequestTo messageRequestTo) {
        Message message = messageMapper.messageRequestToMessage(messageRequestTo);
        if (messageRequestTo.getIssueId() != 0) {
            message.setIssue(issueRepository.findById(messageRequestTo.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found", 404)));
        }

        return messageMapper.messageToMessageResponse(messageRepository.save(message));
    }

    public MessageResponseTo read(@Min(0) int id) throws NotFoundException {
        if (messageRepository.existsById(id)) {
            MessageResponseTo messageResponseTo = messageMapper.messageToMessageResponse(messageRepository.getReferenceById(id));

            return messageResponseTo;
        }

        throw new NotFoundException("Message not found", 404);
    }

    public List<MessageResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction) {
        Pageable p;
        if (direction != null && direction.equals("asc")) {
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).ascending());
        } else {
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).descending());
        }
        Page<Message> res = messageRepository.findAll(p);

        return messageListMapper.toMessageResponseList(res.toList());
    }

    public MessageResponseTo update(@Valid MessageRequestTo messageRequestTo, @Min(0) int id) throws NotFoundException {
        if (messageRepository.existsById(id)) {
            Message message = messageMapper.messageRequestToMessage(messageRequestTo);
            message.setId(id);
            if (messageRequestTo.getIssueId() != 0) {
                message.setIssue(issueRepository.findById(messageRequestTo.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found", 404)));
            }

            return messageMapper.messageToMessageResponse(messageRepository.save(message));
        }

        throw new NotFoundException("Message not found", 404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);

            return true;
        }

        throw new NotFoundException("Message not found", 404);
    }
}
