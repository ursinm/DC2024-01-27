package by.bsuir.test_rw.service.db_interaction.implementations;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.entity.implementations.Creator;
import by.bsuir.test_rw.repository.interfaces.CreatorRepository;
import by.bsuir.test_rw.service.db_interaction.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomCreatorService implements CreatorService {

    private final CreatorRepository creatorRepository;

    @Override
    public Creator findById(Long id) throws EntityNotFoundException {
        return creatorRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<Creator> findAll() {
        return creatorRepository.findAll();
    }

    @Override
    public void save(Creator entity) {
        creatorRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = creatorRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            creatorRepository.deleteById(id);
        }
    }

    @Override
    public void update(Creator entity) {
        boolean wasUpdated = creatorRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            creatorRepository.save(entity);
        }
    }
}
