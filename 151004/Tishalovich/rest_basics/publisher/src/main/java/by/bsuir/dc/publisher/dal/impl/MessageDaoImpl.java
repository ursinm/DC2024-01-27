package by.bsuir.dc.publisher.dal.impl;

import by.bsuir.dc.publisher.dal.MessageDao;
import by.bsuir.dc.publisher.entities.Message;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.MessageSubCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageDaoImpl implements MessageDao {

    private final String discussionUri;

    private final RestClient restClient;

    @Override
    public Message save(Message message) {
        ResponseEntity<Message> result = restClient
                .post()
                .uri(discussionUri)
                .body(message)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            throw new ApiException(
                                    response.getStatusCode().value(),
                                    MessageSubCode.STUB.getSubCode(),
                                    MessageSubCode.STUB.getMessage()
                            );
                        }
                )
                .toEntity(Message.class);

        return null;
    }

    @Override
    public Message update(Message message) {
        ResponseEntity<Message> result = restClient
                .put()
                .uri(discussionUri)
                .body(message)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            throw new ApiException(
                                    response.getStatusCode().value(),
                                    MessageSubCode.STUB.getSubCode(),
                                    MessageSubCode.STUB.getMessage()
                            );
                        }
                )
                .toEntity(Message.class);

        return result.getBody();
    }

    //i need to handle 404 discussion error
    @Override
    public Optional<Message> findById(Long id) {
        ResponseEntity<Message> result = restClient
                .get()
                .uri(discussionUri + "/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            throw new ApiException(
                                    response.getStatusCode().value(),
                                    MessageSubCode.STUB.getSubCode(),
                                    MessageSubCode.STUB.getMessage()
                            );
                        }
                )
                .toEntity(Message.class);

        return Optional.ofNullable(result.getBody());
    }

    @Override
    public Iterable<Message> findAll() {
        ResponseEntity<Message[]> result = restClient
                .get()
                .uri(discussionUri)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            throw new ApiException(
                                    response.getStatusCode().value(),
                                    MessageSubCode.STUB.getSubCode(),
                                    MessageSubCode.STUB.getMessage()
                            );
                        }
                )
                .toEntity(Message[].class);

        return List.of(Objects.requireNonNull(result.getBody()));
    }

    @Override
    public boolean existsById(Long id) {
        Optional<Message> message = findById(id);
        return message.isPresent();
    }

    @Override
    public void deleteById(Long id) {
         ResponseSpec result = restClient
                .delete()
                .uri(discussionUri + "/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            throw new ApiException(
                                    response.getStatusCode().value(),
                                    MessageSubCode.STUB.getSubCode(),
                                    MessageSubCode.STUB.getMessage()
                            );
                        }
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            throw new ApiException(
                                    response.getStatusCode().value(),
                                    MessageSubCode.STUB.getSubCode(),
                                    MessageSubCode.STUB.getMessage()
                            );
                        }
                );
    }

}
