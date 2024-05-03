package discussion.services.impl;

import discussion.dto.NoteRequestTo;
import discussion.dto.NoteResponseTo;
import discussion.entities.Note;
import discussion.exceptions.DeleteException;
import discussion.exceptions.NotFoundException;
import discussion.exceptions.UpdateException;
import discussion.mappers.NoteListMapper;
import discussion.mappers.NoteMapper;
import discussion.repository.NoteRepository;
import discussion.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class ImplNoteService implements NoteService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    NoteListMapper noteListMapper;

    @Override
    public NoteResponseTo getById(Long id) throws NotFoundException {
        Optional<Note> message = noteRepository.findById(id).stream().findFirst();
        return message.map(value -> noteMapper.toNoteResponse(value)).orElseThrow(() -> new NotFoundException("Note not found", 40004L));
    }

    @Override
    public List<NoteResponseTo> getAll() {
        return noteListMapper.toNoteResponseList(noteRepository.findAll());
    }

    @Override
    public NoteResponseTo save(@Valid NoteRequestTo requestTo, String country) {
        Note messageToSave = noteMapper.toNote(requestTo);
        messageToSave.setId(getId());
        messageToSave.setCountry(getCountry(country));
        Note note = noteRepository.save(messageToSave);
        return noteMapper.toNoteResponse(note);
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Optional<Note> message = noteRepository.findById(id).stream().findFirst();
        if (message.isEmpty()) {
            throw new DeleteException("Note not found!", 40004L);
        } else {
            noteRepository.deleteByCountryAndStoryIdAndId(message.get().getCountry(), message.get().getStoryId(), message.get().getId());
        }
    }

    @Override
    public NoteResponseTo update(@Valid NoteRequestTo requestTo, String country) throws UpdateException {
        Note messageToUpdate = noteMapper.toNote(requestTo);
        messageToUpdate.setId(requestTo.getId());
        messageToUpdate.setCountry(getCountry(country));
        Optional<Note> message = noteRepository.findById(requestTo.getId()).stream().findFirst();
        if (message.isEmpty()) {
            throw new UpdateException("Note not found!", 40004L);
        } else {
            noteRepository.deleteByCountryAndStoryIdAndId(message.get().getCountry(), message.get().getStoryId(), message.get().getId());
            return noteMapper.toNoteResponse(noteRepository.save(messageToUpdate));
        }
    }

    @Override
    public List<NoteResponseTo> getByStoryId(Long storyId) throws NotFoundException {
        List<Note> messages = noteRepository.findByStoryId(storyId);
        if (messages.isEmpty()) {
            throw new NotFoundException("Note not found!", 40004L);
        }
        return noteListMapper.toNoteResponseList(messages);
    }

    private long getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        if (requestHeader == null) return "ru";
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, noteRepository.countByCountry(country)*(1-languageMap.get(country)));
        }
        double minValue = Double.MAX_VALUE;
        String minKey = null;

        for (Map.Entry<String, Double> entry : loadMap.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey == null ? "ru" : minKey;
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
