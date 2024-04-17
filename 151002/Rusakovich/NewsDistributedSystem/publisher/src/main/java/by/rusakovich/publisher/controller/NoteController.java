package by.rusakovich.publisher.controller;

import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("${api.url}/notes")
public class NoteController {
    @Autowired
    private RestClient restClient;
    static private final String remoteApiUrl = "http://localhost:24130/api/v1.0/notes";

    @PostMapping("")
    public ResponseEntity<NoteResponseTO> create(@RequestBody @Valid NoteRequestTO to){
        NoteResponseTO result = restClient.post().uri(remoteApiUrl).contentType(MediaType.APPLICATION_JSON).body(to).retrieve().body(NoteResponseTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<?>> readAll(){
        List<?> result = restClient.get().uri(remoteApiUrl).retrieve().body(List.class);
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<NoteResponseTO> read(@PathVariable Long id){
        NoteResponseTO result = restClient.get().uri(remoteApiUrl  +  "/" + id).retrieve().body(NoteResponseTO.class);
        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<NoteResponseTO> update(@Valid @RequestBody NoteRequestTO to){
        NoteResponseTO result = restClient.put().uri(remoteApiUrl).contentType(MediaType.APPLICATION_JSON).body(to).retrieve().body(NoteResponseTO.class);;
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        restClient.delete().uri(remoteApiUrl + "/"+ id).retrieve();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
