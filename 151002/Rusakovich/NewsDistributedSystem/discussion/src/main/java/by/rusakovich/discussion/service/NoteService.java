package by.rusakovich.discussion.service;

import by.rusakovich.discussion.dao.NoteRepository;
import by.rusakovich.discussion.model.Note;
import by.rusakovich.discussion.model.dto.NoteMapper;
import by.rusakovich.discussion.model.dto.NoteRequestTO;
import by.rusakovich.discussion.model.dto.NoteResponseTO;
import by.rusakovich.discussion.service.exceptions.NotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteService implements INoteService {

    private NoteMapper mapper;
    private NoteRepository rep;
    private static final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public NoteResponseTO readById(Long id) {
        var entity = rep.findById(id).stream().findFirst().orElseThrow(NotFound::new);
        return mapper.mapToResponse(entity);
    }

    @Override
    public List<NoteResponseTO> readAll() {
        return rep.findAll().stream()
                .map(mapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponseTO create(NoteRequestTO newEntity, String country) {
        var mappedNewEntity= mapper.mapToEntity(newEntity);
        mappedNewEntity.setId(idGenerator.incrementAndGet());
        mappedNewEntity.setCountry(country);
        var res = rep.save(mappedNewEntity);
        return mapper.mapToResponse(res);
    }

    @Override
    public NoteResponseTO update(NoteRequestTO updatedEntity) {
        var entity = rep.findById(updatedEntity.id()).stream().findFirst().orElseThrow(NotFound::new);
        Note mappedEntityToUpdate = mapper.mapToEntity(updatedEntity);
        rep.deleteByCountryAndNewsIdAndId(entity.getCountry(), entity.getNewsId(), entity.getId());
        mappedEntityToUpdate.setCountry(entity.getCountry());
        var res = rep.save(mappedEntityToUpdate);
        return mapper.mapToResponse(res);
    }

    @Override
    public void deleteById(Long id) {
        var entity = rep.findById(id).stream().findFirst().orElseThrow(NotFound::new);
        rep.deleteByCountryAndNewsIdAndId(entity.getCountry(), entity.getNewsId(), id);
    }

    private long getId (){
        return idGenerator.incrementAndGet();
    }

}
