package by.bsuir.dc.rest_basics.services.common;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Setter
@RequiredArgsConstructor
public abstract class AbstractServiceImpl<I, E, M extends AbstractEntity>
        implements AbstractService<I, E> {

    private final MemoryRepository<M> dao;

    private final EntityMapper<I, E, M> mapper;

    @Override
    public E create(I requestTo) {
        M model = mapper.requestToModel(requestTo);
        Optional<M> savingRes = dao.save(model);
        M res= savingRes.orElseThrow();
        return mapper.modelToResponse(res);
    }

    @Override
    public List<E> getAll() {
        Iterable<M> models = dao.getAll();
        return StreamSupport
                .stream(models.spliterator(), false)
                .map(mapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public E get(Long id) {
        Optional<M> author = dao.getById(id);

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
    @SneakyThrows
    public E update(I requestTo) {
        M model = mapper.requestToModel(requestTo);

        Optional<M> updateRes = dao.update(model);
        if (updateRes.isEmpty()) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        return mapper.modelToResponse(updateRes.get());
    }

    @Override
    @SneakyThrows
    public E delete(Long id) {
        Optional<M> deletingRes = dao.delete(id);
        M model = deletingRes.orElseThrow(
                () -> new ApiException(
                        HttpStatus.NOT_FOUND.value(),
                        GeneralSubCode.WRONG_ID.getSubCode(),
                        GeneralSubCode.WRONG_ID.getMessage()
                )
        );

        return mapper.modelToResponse(model);
    }

}
