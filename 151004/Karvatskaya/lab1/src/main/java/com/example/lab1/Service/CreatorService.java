package com.example.lab1.Service;

import com.example.lab1.DAO.CreatorDao;
import com.example.lab1.DTO.CreatorRequestTo;
import com.example.lab1.DTO.CreatorResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.CreatorListMapper;
import com.example.lab1.Mapper.CreatorMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class CreatorService {

    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    CreatorDao creatorDao;
    @Autowired
    CreatorListMapper creatorListMapper;

    public CreatorResponseTo create(@Valid CreatorRequestTo creator){
        return creatorMapper.creatorToCreatorResponse(creatorDao.create(creatorMapper.creatorRequestToCreator(creator)));
    }
    public List<CreatorResponseTo> readAll(){
        return creatorListMapper.toCreatorResponseList(creatorDao.readAll());
    }
    public CreatorResponseTo read(@Min(0) int id) throws NotFoundException {
        CreatorResponseTo cr = creatorMapper.creatorToCreatorResponse(creatorDao.read(id));
        if(cr != null)
            return cr;
        else
            throw new NotFoundException("Creator not found", 404);
    }
    public CreatorResponseTo update(@Valid CreatorRequestTo creator,  @Min(0) int id){
        CreatorResponseTo cr = creatorMapper.creatorToCreatorResponse(creatorDao.update(creatorMapper.creatorRequestToCreator(creator),id));
        if(cr != null)
            return cr;
        else
            throw new NotFoundException("Creator not found", 404);
    }
    public boolean delete(@Min(0) int id){
        if(creatorDao.delete(id))
            return true;
        else
            throw new NotFoundException("Creator not found", 404);
    }


}
