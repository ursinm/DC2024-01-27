package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.dal.LabelDao;
import by.bsuir.dc.publisher.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.LabelResponseTo;
import by.bsuir.dc.publisher.services.LabelService;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import by.bsuir.dc.publisher.services.exceptions.MessageSubCode;
import by.bsuir.dc.publisher.entities.Label;
import by.bsuir.dc.publisher.services.impl.mappers.LabelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelDao dao;

    private final LabelMapper mapper;

    private final RedisLabelService redisService;

    @Override
    public LabelResponseTo create(LabelRequestTo requestTo) throws ApiException {
        Label model = mapper.requestToModel(requestTo);
        Label savingRes;
        try {
            savingRes = dao.save(model);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN.value(),
                    MessageSubCode.CONSTRAINT_VIOLATION.getSubCode(),
                    e.getMessage()
            );
        }

        LabelResponseTo res = mapper.modelToResponse(savingRes);

        redisService.save(res);

        return res;
    }

    @Override
    public List<LabelResponseTo> getAll() {
        return StreamSupport
                .stream(redisService.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public LabelResponseTo get(Long id) throws ApiException {
        Optional<LabelResponseTo> cachedLabel = redisService.findById(id);
        if (cachedLabel.isPresent()) {
            return cachedLabel.get();
        }

        Optional<Label> author = dao.findById(id);

        if (author.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        LabelResponseTo res = mapper.modelToResponse(author.get());

        redisService.save(res);

        return res;
    }

    @Override
    public LabelResponseTo update(LabelRequestTo requestTo) throws ApiException {
        Label model = mapper.requestToModel(requestTo);

        Label updateRes = dao.save(model);
        //if (updateRes == null) {
        //    throw new ApiException(
        //            HttpStatus.NOT_FOUND.value(),
        //            GeneralSubCode.WRONG_ID.getSubCode(),
        //            GeneralSubCode.WRONG_ID.getMessage()
        //    );
        //}

        LabelResponseTo res = mapper.modelToResponse(updateRes);

        redisService.save(res);

        return res;
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

        redisService.deleteById(id);
    }

}
