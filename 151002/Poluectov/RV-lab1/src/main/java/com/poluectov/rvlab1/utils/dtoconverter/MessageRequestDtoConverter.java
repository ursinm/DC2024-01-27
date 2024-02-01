package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.message.MessageRequestTo;
import com.poluectov.rvlab1.model.Message;
import com.poluectov.rvlab1.repository.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageRequestDtoConverter implements DtoConverter<MessageRequestTo, Message> {

    IssueRepository issueRepository;

    @Override
    public Message convert(MessageRequestTo message) {
        return Message.builder()
                .id(message.getId())
                .issue(issueRepository.find(message.getIssueId()))
                .content(message.getContent())
                .build();
    }
}
