package by.bsuir.news.service;

import by.bsuir.news.config.RestClientConfig;
import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.entity.News;
import by.bsuir.news.entity.Note;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.NewsRepository;
import by.bsuir.news.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NewsRepository newsRepository;
    public NoteResponseTo create(NoteRequestTo request) {
        Optional<News> news = newsRepository.findById(request.getNewsId());
//        if(news.isEmpty()) {
////            throw new ClientException("News with specified id don't exist");
//            return
//        }
        Note note = NoteRequestTo.fromRequest(request);
        note.setNews(news.get());

        return NoteResponseTo.toResponse(note);
    }

    public List<NoteResponseTo> getAll() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream().map(NoteResponseTo::toResponse).collect(Collectors.toList());
    }

    public NoteResponseTo getById(Long id) throws ClientException{
        Optional<Note> note = noteRepository.findById(id);
        if(note.isPresent()) {
            return NoteResponseTo.toResponse(note.get());
        }
        throw new ClientException("Note not found");
    }

    public NoteResponseTo update(NoteRequestTo request) throws ClientException {
        if(noteRepository.findById(request.getId()).isEmpty()) {
            throw new ClientException("Note doesn't exist");
        }
        Optional<News> news = newsRepository.findById(request.getNewsId());
        if(news.isEmpty()) {
            throw new ClientException("News with specified id don't exist");
        }
        Note note = NoteRequestTo.fromRequest(request);
        note.setNews(news.get());
        noteRepository.save(note);
        return NoteResponseTo.toResponse(note);
    }

    public Long delete(Long id) throws ClientException {
        if(noteRepository.findById(id).isEmpty()) {
            throw new ClientException("Note doesn't exist");
        }
        noteRepository.deleteById(id);
        if(noteRepository.findById(id).isPresent()) {
            throw new ClientException("Failed to delete the note");
        }
        return id;
    }
}
