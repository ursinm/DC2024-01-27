package by.rusakovich.publisher.service.impl;

import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import by.rusakovich.publisher.service.IEntityService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService implements IEntityService<Long, NoteRequestTO, NoteResponseTO> {
    private final RestClient restClient;
    private static final String remoteApiUrl = "http://localhost:24130/api/v1.0/notes";
    public static final ParameterizedTypeReference<List<NoteResponseTO>> LIST_NOTE_RESPONSE_TO = new ParameterizedTypeReference<>() {};

    @Override
    public NoteResponseTO readById(Long id) {
        return restClient.get().uri(remoteApiUrl  +  "/" + id).retrieve().body(NoteResponseTO.class);
    }

    @Override
    public List<NoteResponseTO> readAll() {
        return restClient.get().uri(remoteApiUrl).retrieve().body(LIST_NOTE_RESPONSE_TO);
    }


    @Override
    public NoteResponseTO create(NoteRequestTO newEntity) {
        return restClient.post().uri(remoteApiUrl).contentType(MediaType.APPLICATION_JSON).body(newEntity).retrieve().body(NoteResponseTO.class);
    }

    @Override
    public NoteResponseTO update(NoteRequestTO updatedEntity) {
        return restClient.put().uri(remoteApiUrl).contentType(MediaType.APPLICATION_JSON).body(updatedEntity).retrieve().body(NoteResponseTO.class);
    }

    @Override
    public void deleteById(Long id) {
        restClient.delete().uri(remoteApiUrl + "/"+ id).retrieve();
    }
}
