package com.poluectov.rvlab1.service;

import com.poluectov.rvlab1.dto.message.MessageRequestTo;
import com.poluectov.rvlab1.dto.message.MessageResponseTo;
import com.poluectov.rvlab1.model.Message;
import com.poluectov.rvlab1.repository.MessageRepository;
import com.poluectov.rvlab1.utils.dtoconverter.IssueResponseDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class MessageService extends CommonRestService<Message, MessageRequestTo, MessageResponseTo> {

    IssueResponseDtoConverter issueResponseDtoConverter;

    public MessageService(MessageRepository repository,
                          IssueResponseDtoConverter issueResponseDtoConverter) {
        super(repository);
        this.issueResponseDtoConverter = issueResponseDtoConverter;
    }

    MessageResponseTo mapResponseTo(Message message) {
        return MessageResponseTo.builder()
                .id(message.getId())
                .issue(issueResponseDtoConverter.convert(message.getIssue()))
                .content(message.getContent())
                .build();
    }
}
