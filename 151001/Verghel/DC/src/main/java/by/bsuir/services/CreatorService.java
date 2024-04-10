package by.bsuir.services;

import by.bsuir.dao.CreatorDao;
import by.bsuir.dto.CreatorRequestTo;
import by.bsuir.dto.CreatorResponseTo;
import by.bsuir.entities.Creator;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.CreatorListMapper;
import by.bsuir.mapper.CreatorMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CreatorService {
    @Autowired
    CreatorMapper CreatorMapper;
    @Autowired
    CreatorDao CreatorDao;
    @Autowired
    CreatorListMapper CreatorListMapper;

    public CreatorResponseTo getCreatorById(@Min(0) Long id) throws NotFoundException{
        Optional<Creator> Creator = CreatorDao.findById(id);
        return Creator.map(value -> CreatorMapper.CreatorToCreatorResponse(value)).orElseThrow(() -> new NotFoundException("Creator not found!", 40004L));
    }

    public List<CreatorResponseTo> getCreators() {
        return CreatorListMapper.toCreatorResponseList(CreatorDao.findAll());
    }

    public CreatorResponseTo saveCreator(@Valid CreatorRequestTo Creator) {
        Creator CreatorToSave = CreatorMapper.CreatorRequestToCreator(Creator);
        return CreatorMapper.CreatorToCreatorResponse(CreatorDao.save(CreatorToSave));
    }

    public void deleteCreator(@Min(0) Long id) throws DeleteException {
        CreatorDao.delete(id);
    }

    public CreatorResponseTo updateCreator(@Valid CreatorRequestTo Creator) throws UpdateException {
        Creator CreatorToUpdate = CreatorMapper.CreatorRequestToCreator(Creator);
        return CreatorMapper.CreatorToCreatorResponse(CreatorDao.update(CreatorToUpdate));
    }

}
