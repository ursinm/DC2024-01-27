package by.bsuir.test_rw.service.db_interaction.implementations;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.entity.implementations.Tag;
import by.bsuir.test_rw.repository.interfaces.TagRepository;
import by.bsuir.test_rw.service.db_interaction.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomTagService implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Tag findById(Long id) throws EntityNotFoundException {
        return tagRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void save(Tag entity) {
        tagRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = tagRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            tagRepository.deleteById(id);
        }
    }

    @Override
    public void update(Tag entity) {
        boolean wasUpdated = tagRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            tagRepository.save(entity);
        }
    }
}
