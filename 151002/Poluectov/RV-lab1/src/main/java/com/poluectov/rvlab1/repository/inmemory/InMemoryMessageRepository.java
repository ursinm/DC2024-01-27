package com.poluectov.rvlab1.repository.inmemory;

import com.poluectov.rvlab1.dto.message.MessageRequestTo;
import com.poluectov.rvlab1.model.Message;
import com.poluectov.rvlab1.repository.IssueRepository;
import com.poluectov.rvlab1.repository.MessageRepository;
import com.poluectov.rvlab1.utils.dtoconverter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMessageRepository extends InMemoryRepository<Message, MessageRequestTo> implements MessageRepository {

    IssueRepository issueRepository;
    public InMemoryMessageRepository(DtoConverter<MessageRequestTo, Message> convert,
                                     IssueRepository issueRepository) {
        super(convert);
        this.issueRepository = issueRepository;
    }

    @Override
    public Message save(MessageRequestTo request) {
        if (issueRepository.find(request.getIssueId()) == null){
            return null;
        }

        return super.save(request);
    }

}
