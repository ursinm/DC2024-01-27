package by.bsuir.services;

import by.bsuir.dto.CreatorRequestTo;
import by.bsuir.dto.CreatorResponseTo;
import by.bsuir.entities.Creator;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.DuplicationException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.CreatorListMapper;
import by.bsuir.mapper.CreatorMapper;
import by.bsuir.repository.CreatorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CreatorService {
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    CreatorRepository creatorDao;
    @Autowired
    CreatorListMapper creatorListMapper;

    public CreatorResponseTo getCreatorById(@Min(0) Long id) throws NotFoundException {
        Optional<Creator> creator = creatorDao.findById(id);
        return creator.map(value -> creatorMapper.creatorToCreatorResponse(value)).orElseThrow(() -> new NotFoundException("Creator not found!", 40004L));
    }

    public List<CreatorResponseTo> getCreators(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Creator> creators = creatorDao.findAll(pageable);
        return creatorListMapper.toCreatorResponseList(creators.toList());
    }

    public CreatorResponseTo saveCreator(@Valid CreatorRequestTo creator) throws DuplicationException{
        Creator creatorToSave = creatorMapper.creatorRequestToCreator(creator);
        if (creatorDao.existsByLogin(creatorToSave.getLogin())){
            throw new DuplicationException("Login duplication", 40005L);
        }
        return creatorMapper.creatorToCreatorResponse(creatorDao.save(creatorToSave));
    }

    public void deleteCreator(@Min(0) Long id) throws DeleteException {
        if (!creatorDao.existsById(id)) {
            throw new DeleteException("Creator not found!", 40004L);
        } else {
            creatorDao.deleteById(id);
        }
    }

    public CreatorResponseTo updateCreator(@Valid CreatorRequestTo creator) throws UpdateException {
        Creator creatorToUpdate = creatorMapper.creatorRequestToCreator(creator);
        if (!creatorDao.existsById(creatorToUpdate.getId())){
            throw new UpdateException("Creator not found!", 40004L);
        } else {
            return creatorMapper.creatorToCreatorResponse(creatorDao.save(creatorToUpdate));
        }
    }

    public CreatorResponseTo getCreatorByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Creator creator = creatorDao.findCreatorByIssueId(issueId);
        return creatorMapper.creatorToCreatorResponse(creator);
    }
}
