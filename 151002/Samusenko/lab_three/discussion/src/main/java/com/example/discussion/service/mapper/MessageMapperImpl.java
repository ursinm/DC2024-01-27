package com.example.discussion.service.mapper;


import com.example.discussion.model.entity.Message;
import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageMapperImpl implements MessageMapper{
    private static final SecureRandom random;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }
    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }
    @Override
    public MessageRequestTo messageToRequestTo(Message message) {
        return new MessageRequestTo(
                message.getId().longValue(),
                message.getIssueId().longValue(),
                message.getContent()
        );
    }

    @Override
    public List<MessageRequestTo> messageToRequestTo(Iterable<Message> messages) {
        return StreamSupport.stream(messages.spliterator(), false)
                .map(this::messageToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Message dtoToEntity(MessageRequestTo messageRequestTo, String country) {
        if(messageRequestTo.id() == null)
            return new Message(
                    null,
                    new BigInteger(messageRequestTo.issueId().toString()),
                    country,
                    messageRequestTo.content()
            );
        else
            return new Message(
                    new BigInteger(messageRequestTo.id().toString()),
                    new BigInteger(messageRequestTo.issueId().toString()),
                    country,
                    messageRequestTo.content()
            );
    }

    @Override
    public MessageResponseTo messageToResponseTo(Message message) {
        return new MessageResponseTo(
                message.getId().longValue(),
                message.getIssueId().longValue(),
                message.getContent());
    }

    @Override
    public List<MessageResponseTo> messageToResponseTo(Iterable<Message> messages) {
        return StreamSupport.stream(messages.spliterator(), false)
                .map(this::messageToResponseTo)
                .collect(Collectors.toList());
    }

}
