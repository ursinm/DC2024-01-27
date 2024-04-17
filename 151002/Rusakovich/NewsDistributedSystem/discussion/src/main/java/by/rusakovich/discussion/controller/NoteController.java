package by.rusakovich.discussion.controller;

import by.rusakovich.discussion.model.dto.NoteRequestTO;
import by.rusakovich.discussion.model.dto.NoteResponseTO;
import by.rusakovich.discussion.service.INoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.url}/notes")
@RequiredArgsConstructor
public class NoteController{

    private final INoteService service;

    @PostMapping("")
    public ResponseEntity<NoteResponseTO> create(@RequestBody @Valid NoteRequestTO to){
        NoteResponseTO result = service.create(to);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("")
    public ResponseEntity<List<NoteResponseTO>> readAll(){
        List<NoteResponseTO> result = service.readAll();
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<NoteResponseTO> read(@PathVariable Long id){
        NoteResponseTO result = service.readById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<NoteResponseTO> update(@Valid @RequestBody NoteRequestTO to){
        NoteResponseTO result = service.update(to);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
