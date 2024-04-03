package by.bsuir.dc.discussion.service;

import by.bsuir.dc.discussion.dal.MessageDao;
import by.bsuir.dc.discussion.entity.Message;
import by.bsuir.dc.discussion.entity.MessageRequestTo;
import by.bsuir.dc.discussion.entity.MessageResponseTo;
import by.bsuir.dc.discussion.service.exception.ApiException;
import by.bsuir.dc.discussion.service.exception.GeneralSubCode;
import by.bsuir.dc.discussion.service.exception.MessageSubCode;
import by.bsuir.dc.discussion.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao dao;

    private final MessageMapper mapper;

    //I think that random have no issues with synchronization
    private final Random random = new Random();

    public MessageResponseTo create(MessageRequestTo requestTo) throws ApiException {
        Message model = mapper.requestToModel(requestTo);
        model.setId(random.nextLong());
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
