package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.MessageDao;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import by.bsuir.dc.publisher.services.impl.mappers.MessageMapper;
import by.bsuir.dc.publisher.entities.Message;
import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.MessageResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@Service
@RequiredArgsConstructor
public class MessageServiceImpl {

    private final String discussionUri;

    private final MessageDao dao;

    private final RestClient restClient;

    private final MessageMapper mapper;

    private String getCountry(String lang) {
        int commaIndex = lang.indexOf(',');
        String country = lang;

        if (commaIndex != -1) {
            country = lang.substring(0, commaIndex);
        }

        return country;
    }

    public MessageResponseTo create(MessageRequestTo requestTo, String lang)
            throws ApiException {

        String country = getCountry(lang);

        Message model = mapper.requestToModel(requestTo);
        model.setCountry(country);

        ResponseEntity<Message> result = restClient
                .post()
                .uri(discussionUri)
                .body(model)
                .retrieve()
                .onStatus(
                    HttpStatusCode::is5xxServerError,
                    (request, response) -> {
                        throw new ApiException(
                                response.getStatusCode().value(),
                                1,//change
                                "Some error message"//change here
                        );
                    }
                )
                .toEntity(Message.class);

        Message savingRes = result.getBody();
        return mapper.modelToResponse(savingRes);
    }

    public List<MessageResponseTo> getAll() {
        Iterable<Message> models = dao.findAll();
        return StreamSupport
                .stream(models.spliterator(), false)
                .map(mapper::modelToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponseTo get(Long id) throws ApiException {
        Optional<Message> author = dao.findById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        return mapper.modelToResponse(author.get());
    }

    public MessageResponseTo update(MessageRequestTo requestTo) throws ApiException {
        Message model = mapper.requestToModel(requestTo);

        Message updateRes = dao.update(model);
        //if (updateRes == null) {
        //    throw new ApiException(
        //            HttpStatus.NOT_FOUND.value(),
        //            GeneralSubCode.WRONG_ID.getSubCode(),
        //            GeneralSubCode.WRONG_ID.getMessage()
        //    );
        //}

        return mapper.modelToResponse(updateRes);
    }

    public void delete(Long id) throws ApiException {
        if (!dao.existsById(id)) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        dao.deleteById(id);
    }

}
