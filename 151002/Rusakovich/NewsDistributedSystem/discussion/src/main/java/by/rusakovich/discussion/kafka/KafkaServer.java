package by.rusakovich.discussion.kafka;

import by.rusakovich.discussion.model.dto.NoteRequestTO;
import by.rusakovich.discussion.model.dto.NoteResponseTO;
import by.rusakovich.discussion.service.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@EnableKafka
@Component
@RequiredArgsConstructor
public class KafkaServer {
    public static final String REQUEST_TOPIC = "InTopic";
    public static final String RESPONSE_TOPIC = "OutTopic";
    private final KafkaTemplate<String, String> sender;
    private final ObjectMapper jsonMapper;
    private final NoteService noteService;

    @KafkaListener(topics = REQUEST_TOPIC, groupId = REQUEST_TOPIC)
    private void listenNote(ConsumerRecord<String, String> record) {
        UUID uuid = UUID.fromString(record.key());
        try {
            NoteEvent noteEvent = jsonMapper.readValue(record.value(), NoteEvent.class);
            NoteRequestTO noteRequest = noteEvent.request();
            List<NoteResponseTO> response = switch (noteEvent.operation()){
                // include country in event?
                case CREATE ->List.of(noteService.create(noteRequest, "by"));
                case UPDATE -> List.of(noteService.update(noteRequest));
                case FIND_ALL -> noteService.readAll();
                case FIND_BY_ID -> List.of(noteService.readById(noteRequest.id()));
                case REMOVE_BY_ID -> {
                    noteService.deleteById(noteRequest.id());
                    yield null;
                }

            };
            NoteEvent result = new NoteEvent(response);
            sender.send(RESPONSE_TOPIC, uuid.toString(), jsonMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}