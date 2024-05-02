package discussion.controllers;

import discussion.dto.NoteRequestTo;
import discussion.dto.NoteResponseTo;
import discussion.exceptions.DeleteException;
import discussion.services.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {
    @Autowired
    NoteService noteService;

    @Autowired
    private KafkaSender kafkaSender;
    private String topic = "OutTopic";

    @KafkaListener(topics = "InTopic", groupId = "inGroup",
            containerFactory = "noteRequestToConcurrentKafkaListenerContainerFactory")
    void listenerWithMessageConverter(@Payload NoteRequestTo noteRequestTo) {
        switch (noteRequestTo.getMethod()) {
            case "GET" -> {
                if (noteRequestTo.getId() != null) {
                    kafkaSender.sendCustomMessage(getNote(noteRequestTo.getId()), topic);
                }
            }
            case "DELETE" -> kafkaSender.sendCustomMessage(deleteNote(noteRequestTo.getId()), topic);
            case "POST" -> kafkaSender.sendCustomMessage(saveNote(noteRequestTo.getCountry(), noteRequestTo), topic);
            case "PUT" -> kafkaSender.sendCustomMessage(updateNote(noteRequestTo.getCountry(), noteRequestTo), topic);
        }
    }

    @GetMapping
    public List<NoteResponseTo> getNotes() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public NoteResponseTo getNote(@PathVariable Long id) {
        return noteService.getById(id);
    }

    @DeleteMapping("/{id}")
    public NoteResponseTo deleteNote(@PathVariable Long id) {
        NoteResponseTo noteResponseTo = new NoteResponseTo();
        try {
            noteService.delete(id);
        } catch (DeleteException e) {
            noteResponseTo.setContent("DELETE EXCEPTION");
        }
        return noteResponseTo;
    }

    private String getLanguage(HttpServletRequest httpServletRequest) {
        String acceptLanguageHeader = httpServletRequest.getHeader("Accept-Language");
        return acceptLanguageHeader == null ? "ru-RU, ru;q=0.9" : acceptLanguageHeader;
    }

    @PostMapping
    public NoteResponseTo saveNote(String language, @RequestBody NoteRequestTo noteRequestTo) {
        return noteService.save(noteRequestTo, language);
    }

    @PutMapping
    public NoteResponseTo updateNote(String language, @RequestBody NoteRequestTo noteRequestTo) {
        return noteService.update(noteRequestTo, language);
    }

    @GetMapping("/story/{id}")
    public List<NoteResponseTo> getEditorByIssueId(@PathVariable Long id) {
        return noteService.getByStoryId(id);
    }
}