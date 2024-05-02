package by.bsuir.controllers;

import by.bsuir.dto.MessageRequestTo;
import by.bsuir.dto.MessageResponseTo;
import by.bsuir.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "messageRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithMessageConverter(@Payload MessageRequestTo messageRequestTo) {
        if (Objects.equals(messageRequestTo.getMethod(), "GET")) {
            if (messageRequestTo.getId() != null) {
                kafkaSender.sendCustomMessage(getMessage(messageRequestTo.getId()), topic);
            } else {
                // kafkaSender.sendCustomMessage(getComments());
            }
        } else {
            if (Objects.equals(messageRequestTo.getMethod(), "DELETE")) {
                kafkaSender.sendCustomMessage(deleteMessage(messageRequestTo.getId()), topic);
            } else {
                if (Objects.equals(messageRequestTo.getMethod(), "POST")) {
                    kafkaSender.sendCustomMessage(saveMessage(messageRequestTo.getCountry(), messageRequestTo), topic);
                } else {
                    if (Objects.equals(messageRequestTo.getMethod(), "PUT")) {
                        kafkaSender.sendCustomMessage(updateMessage(messageRequestTo.getCountry(), messageRequestTo), topic);
                    }
                }
            }
        }
    }

    @GetMapping
    public List<MessageResponseTo> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/{id}")
    public MessageResponseTo getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @DeleteMapping("/{id}")
    public MessageResponseTo deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return new MessageResponseTo();
    }

    @PostMapping
    public MessageResponseTo saveMessage(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody MessageRequestTo message) {
        return messageService.saveMessage(message, acceptLanguageHeader);
    }

    @PutMapping
    public MessageResponseTo updateMessage(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody MessageRequestTo message) {
        return messageService.updateMessage(message, acceptLanguageHeader);
    }

    @GetMapping("/byIssue/{id}")
    public List<MessageResponseTo> getEditorByIssueId(@PathVariable Long id) {
        return messageService.getMessageByIssueId(id);
    }
}
