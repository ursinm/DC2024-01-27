package com.example.discussion.service.mapper;


import com.example.discussion.model.entity.Message;
import com.example.discussion.model.entity.MessageKey;
import com.example.discussion.model.entity.MessageState;
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
                message.getKey().getId().longValue(),
                message.getKey().getIssueId().longValue(),
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

        MessageKey keyRes = new MessageKey();
        keyRes.setIssueId(new BigInteger(messageRequestTo.getIssueId().toString()));
        keyRes.setMessage_country("local");
        if(messageRequestTo.getId() == null){
            keyRes.setId(new BigInteger(getTimeBasedId().toString()));
        }
        else
            keyRes.setId(new BigInteger(messageRequestTo.getId().toString()));
        Message res = new Message(keyRes, messageRequestTo.getContent(), MessageState.APPROVE);
        return res;
    }

    @Override
    public MessageResponseTo messageToResponseTo(Message message) {
        return new MessageResponseTo(
                message.getKey().getId(),
                message.getKey().getIssueId(),
                message.getContent());
    }

    @Override
    public List<MessageResponseTo> messageToResponseTo(Iterable<Message> messages) {
        return StreamSupport.stream(messages.spliterator(), false)
                .map(this::messageToResponseTo)
                .collect(Collectors.toList());
    }

}
