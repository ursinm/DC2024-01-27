package by.rusakovich.discussion.service;

import by.rusakovich.discussion.dao.INoteRepository;
import by.rusakovich.discussion.model.Note;
import by.rusakovich.discussion.model.dto.NoteMapper;
import by.rusakovich.discussion.model.dto.NoteRequestTO;
import by.rusakovich.discussion.model.dto.NoteResponseTO;
import by.rusakovich.discussion.service.exceptions.CantCreate;
import by.rusakovich.discussion.service.exceptions.NotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class NoteService implements INoteService {

    private NoteMapper mapper;
    private INoteRepository rep;

    @Override
    public NoteResponseTO readById(Long id) {
        var entity = rep.readById(id).orElseThrow(() -> new NotFound(id));
        return mapper.mapToResponse(entity);
    }

    @Override
    public List<NoteResponseTO> readAll() {
        return StreamSupport.stream(rep.readAll().spliterator(), false)
                .map(mapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponseTO create(NoteRequestTO newEntity) {
        var mappedNewEntity= mapper.mapToEntity(newEntity);
        var entity = rep.create(mappedNewEntity).orElseThrow(CantCreate::new);
        return mapper.mapToResponse(entity);
    }

    @Override
    public NoteResponseTO update(NoteRequestTO updatedEntity) {
        Note mappedEntityToUpdate = mapper.mapToEntity(updatedEntity);
        var entity = rep.update(mappedEntityToUpdate).orElseThrow(() -> new NotFound(updatedEntity.id()));
        return mapper.mapToResponse(entity);
    }

    @Override
    public void deleteById(Long id) {
        rep.removeById(id);
    }
}
