package by.bsuir.services;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.entities.Message;
import by.bsuir.entities.MessageKey;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.repository.MessageRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
import java.util.*;

@Service
@Validated
public class MessageService {
    @Autowired
    MessageRepository messageDao;

    public MessageResponseTo getMessageById(int id) throws NotFoundException {
        Optional<Message> message = messageDao.findById(id).stream().findFirst();
        return message.map(this::messageToMessageResponse).orElseThrow(() -> new NotFoundException("Message not found!", 40004));
    }

    public List<MessageResponseTo> getMessages() {
        return toMessageResponseList(messageDao.findAll());
    }

    public MessageResponseTo saveMessage(@Valid MessageRequestTo message, String country) {
        Message messageToSave = messageRequestToMessage(message);
        messageToSave.setId(getId());
        messageToSave.setCountry(getCountry(country));
        return messageToMessageResponse(messageDao.save(messageToSave));
    }

    public void deleteMessage(int id) throws DeleteException {
        Optional<Message> message = messageDao.findById(id).stream().findFirst();
        if (message.isEmpty()) {
            throw new DeleteException("Message not found!", 40004);
        } else {
            messageDao.deleteByCountryAndIssueIdAndId(message.get().getCountry(), message.get().getIssueId(), message.get().getId());
        }
    }

    public MessageResponseTo updateMessage(@Valid MessageRequestTo message, String country) throws UpdateException {
        Message messageToUpdate = messageRequestToMessage(message);
        messageToUpdate.setId(message.getId());
        messageToUpdate.setCountry(getCountry(country));
        Optional<Message> messageOptional = messageDao.findById(message.getId()).stream().findFirst();
        if (messageOptional.isEmpty()) {
            throw new UpdateException("Message not found!", 40004);
        } else {
            messageDao.deleteByCountryAndIssueIdAndId(messageOptional.get().getCountry(), messageOptional.get().getIssueId(), messageOptional.get().getId());
            return messageToMessageResponse(messageDao.save(messageToUpdate));
        }
    }

    public List<MessageResponseTo> getMessageByIssueId(int issueId) throws NotFoundException {
        List<Message> message = messageDao.findByIssueId(issueId);
        if (message.isEmpty()) {
            throw new NotFoundException("Message not found!", 40004);
        }
        return toMessageResponseList(message);
    }

    private MessageResponseTo messageToMessageResponse(Message message) {
        MessageResponseTo messageResponseTo = new MessageResponseTo();
        messageResponseTo.setIssueId(message.getIssueId());
        messageResponseTo.setId(message.getId());
        messageResponseTo.setContent(message.getContent());
        return messageResponseTo;
    }

    private List<MessageResponseTo> toMessageResponseList(List<Message> messages) {
        List<MessageResponseTo> response = new ArrayList<>();
        for (Message message : messages) {
            response.add(messageToMessageResponse(message));
        }
        return response;
    }

    private Message messageRequestToMessage(MessageRequestTo requestTo) {
        Message message = new Message();
        message.setId(requestTo.getId());
        message.setContent(requestTo.getContent());
        message.setIssueId(requestTo.getIssueId());
        return message;
    }

    private int getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, messageDao.countByCountry(country)*(1-languageMap.get(country)));
        }
        double minValue = Double.MAX_VALUE;
        String minKey = null;

        for (Map.Entry<String, Double> entry : loadMap.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    private static Map<String, Double> getStringDoubleMap(String requestHeader) {
        String[] languages = requestHeader.split(",");
        Map<String, Double> languageMap = new HashMap<>();
        for (String language : languages) {
            String[] parts = language.split(";");
            String lang = parts[0].trim();
            double priority = 1.0; // По умолчанию
            if (parts.length > 1) {
                String[] priorityParts = parts[1].split("=");
                priority = Double.parseDouble(priorityParts[1]);
            }
            languageMap.put(lang, priority);
        }
        return languageMap;
    }
}
