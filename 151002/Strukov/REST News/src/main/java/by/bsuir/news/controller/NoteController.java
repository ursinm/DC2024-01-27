package by.bsuir.news.controller;

import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.entity.Note;
import by.bsuir.news.service.GenericService;
import by.bsuir.news.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    @Value("${app.notes-path}")
    private String notesPath;
    private final RestClient restClient;

    private static void AddHeadersWithoutLength(HttpHeaders httpHeaders, HttpHeaders added) {
        httpHeaders.addAll(added);
        httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
    }

    @GetMapping
    public ResponseEntity<?> getAllNotes(@RequestHeader HttpHeaders headers) {
        return restClient.get().uri(notesPath).headers(httpHeaders -> AddHeadersWithoutLength(httpHeaders, headers)).retrieve().toEntity(String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@RequestHeader HttpHeaders headers, @PathVariable Map<String, Object> id) {
        return restClient.get().uri(notesPath + "/{id}", id).headers(httpHeaders -> AddHeadersWithoutLength(httpHeaders, headers)).retrieve().toEntity(String.class);
    }

    @PostMapping
    public ResponseEntity<?> saveNote(@RequestHeader HttpHeaders headers, @RequestBody Object note) {
        return restClient.post().uri(notesPath).headers(httpHeaders -> AddHeadersWithoutLength(httpHeaders, headers)).body(note).retrieve().toEntity(String.class);
    }

    @PutMapping
    public ResponseEntity<?> updateNote(@RequestHeader HttpHeaders headers, @RequestBody Object note) {
        return restClient.put().uri(notesPath).headers(httpHeaders -> AddHeadersWithoutLength(httpHeaders, headers)).body(note).retrieve().toEntity(String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@RequestHeader HttpHeaders headers, @PathVariable Map<String, Object> id) {
        return restClient.delete().uri(notesPath + "/{id}", id).headers(httpHeaders -> AddHeadersWithoutLength(httpHeaders, headers)).retrieve().toEntity(String.class);
    }
}
