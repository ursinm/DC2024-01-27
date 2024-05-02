package com.example.discussion.controller;

import com.example.discussion.kafka.KafkaSender;
import com.example.discussion.model.request.NoteRequestTo;
import com.example.discussion.model.response.NoteResponseTo;
import com.example.discussion.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @Autowired
     KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "commentRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithMessageConverter(@Payload NoteRequestTo commentRequestTo) {
        if (Objects.equals(commentRequestTo.getMethod(), "GET")) {
            if (commentRequestTo.getId() != null) {
                kafkaSender.sendCustomMessage(getNote(commentRequestTo.getId()), topic);
            } else {
                // kafkaSender.sendCustomMessage(getComments());
            }
        } else {
            if (Objects.equals(commentRequestTo.getMethod(), "DELETE")) {
                kafkaSender.sendCustomMessage(deleteNote(commentRequestTo.getId()), topic);
            } else {
                if (Objects.equals(commentRequestTo.getMethod(), "POST")) {
                    kafkaSender.sendCustomMessage(saveNote(commentRequestTo.getCountry(), commentRequestTo), topic);
                } else {
                    if (Objects.equals(commentRequestTo.getMethod(), "PUT")) {
                        kafkaSender.sendCustomMessage(updateComment(commentRequestTo.getCountry(), commentRequestTo), topic);
                    }
                }
            }
        }
    }

    @GetMapping
    public List<NoteResponseTo> getComments() {
        return noteService.getNotes();
    }

    @DeleteMapping("/{id}")
    public NoteResponseTo deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return new NoteResponseTo();
    }

    @GetMapping("/{id}")
    public NoteResponseTo getNote(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping
    public NoteResponseTo saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                   @RequestBody NoteRequestTo noteRequestTo) {
        return noteService.saveNote(noteRequestTo, acceptLanguageHeader);
    }

    @PutMapping()
    public NoteResponseTo updateComment(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader,
                                        @RequestBody NoteRequestTo noteRequestTo) {
        return noteService.updateNote(noteRequestTo, acceptLanguageHeader);
    }


}