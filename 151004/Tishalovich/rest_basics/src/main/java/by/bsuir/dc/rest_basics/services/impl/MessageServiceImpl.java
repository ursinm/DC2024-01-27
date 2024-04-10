package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.MessageDao;
import by.bsuir.dc.rest_basics.entities.Message;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.rest_basics.services.MessageService;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.exceptions.MessageSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageDao dao;

    private final MessageMapper mapper;

    @Override
    public MessageResponseTo create(MessageRequestTo requestTo) throws ApiException {
        Message model = mapper.requestToModel(requestTo);
        Message savingRes;
        try {
            savingRes = dao.save(model);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN.value(),
                    MessageSubCode.CONSTRAINT_VIOLATION.getSubCode(),
                    e.getMessage()
            );
        }
        return mapper.modelToResponse(savingRes);
    }

    @Override
    public List<MessageResponseTo> getAll() {
        Iterable<Message> models = dao.findAll();
        return StreamSupport
                .stream(models.spliterator(), false)
                .map(mapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
    public MessageResponseTo update(MessageRequestTo requestTo) throws ApiException {
        Message model = mapper.requestToModel(requestTo);

        Message updateRes = dao.save(model);
        //if (updateRes == null) {
        //    throw new ApiException(
        //            HttpStatus.NOT_FOUND.value(),
        //            GeneralSubCode.WRONG_ID.getSubCode(),
        //            GeneralSubCode.WRONG_ID.getMessage()
        //    );
        //}

        return mapper.modelToResponse(updateRes);
    }

    @Override
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
