package by.bsuir.dc.discussion.service;

import by.bsuir.dc.discussion.dal.MessageDao;
import by.bsuir.dc.discussion.entity.Message;
import by.bsuir.dc.discussion.entity.MessageRequestTo;
import by.bsuir.dc.discussion.entity.MessageResponseTo;
import by.bsuir.dc.discussion.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao dao;

    private final MessageMapper mapper;

    public MessageResponseTo create(MessageRequestTo requestTo) {
        Message message = mapper.requestToModel(requestTo);
        message.setId(new Random().nextLong());
        Message saved = dao.save(message);
        return mapper.modelToResponse(saved);
    }

    public Iterable<MessageResponseTo> getAll() {
        List<MessageResponseTo> res = new ArrayList<>();

        Iterable<Message> messages = dao.findAll();
        for (Message message : messages) {
            res.add(mapper.modelToResponse(message));
        }

        return res;
    }

}
