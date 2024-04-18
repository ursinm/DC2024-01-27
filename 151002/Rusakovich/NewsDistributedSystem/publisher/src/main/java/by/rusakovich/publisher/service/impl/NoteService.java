package by.rusakovich.publisher.service.impl;

import by.rusakovich.publisher.kafka.KafkaClient;
import by.rusakovich.publisher.kafka.NoteEvent;
import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import by.rusakovich.publisher.service.IEntityService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService implements IEntityService<Long, NoteRequestTO, NoteResponseTO> {
    private final KafkaClient kafkaClient;

    @Override
    public NoteResponseTO readById(Long id) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_BY_ID, id);
        NoteEvent result= kafkaClient.sync(noteEvent);
        return result.responses().getFirst();
    }

    @Override
    public List<NoteResponseTO> readAll() {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_ALL);
        NoteEvent result= kafkaClient.sync(noteEvent);
        return result.responses();
    }


    @Override
    public NoteResponseTO create(NoteRequestTO newEntity) {
        // check newsId here
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.CREATE, newEntity);
        NoteEvent result= kafkaClient.sync(noteEvent);
        return result.responses().getFirst();
    }

    @Override
    public NoteResponseTO update(NoteRequestTO updatedEntity) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.UPDATE, updatedEntity);
        NoteEvent result= kafkaClient.sync(noteEvent);
        return result.responses().getFirst();
    }

    @Override
    public void deleteById(Long id) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_BY_ID, id);
        NoteEvent result= kafkaClient.sync(noteEvent);
    }
}
