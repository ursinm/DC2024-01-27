package by.bsuir.news.service;

import by.bsuir.news.dto.request.EditorRequestTo;
import by.bsuir.news.dto.response.EditorResponseTo;
import by.bsuir.news.entity.Editor;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EditorService {
    @Autowired
    private EditorRepository repository;
    public EditorResponseTo create(EditorRequestTo request) throws ClientException {
        Editor editor = EditorRequestTo.fromRequest(request);
        return EditorResponseTo.toResponse(repository.save(editor));
    }

    public List<EditorResponseTo> getAll() {
        List<Editor> editors = repository.findAll();
        return editors.stream().map(EditorResponseTo::toResponse).collect(Collectors.toList());
    }

    public EditorResponseTo getById(Long id) throws ClientException {
        Optional<Editor> editor = repository.findById(id);
        if(editor.isPresent()) {
            return EditorResponseTo.toResponse(editor.get());
        }
        else {
            throw new ClientException("Editor not found");
        }
    }

    public EditorResponseTo update(EditorRequestTo request) throws ClientException {
        Editor editor = EditorRequestTo.fromRequest(request);
        if(repository.findById(editor.getId()).isPresent()) {
            repository.save(editor);
            return EditorResponseTo.toResponse(editor);
        }
        throw new ClientException("No editor to update");
    }

    public Long delete(Long id) throws ClientException {
        if(repository.findById(id).isEmpty()) {
            throw new ClientException("No editor to delete");
        }
        repository.deleteById(id);
        if(repository.findById(id).isPresent()) {
            throw new ClientException("Failed to delete the editor");
        }
        return id;
    }
}
