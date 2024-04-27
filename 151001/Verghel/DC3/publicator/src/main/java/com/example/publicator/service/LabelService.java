package com.example.publicator.service;

import com.example.publicator.dto.LabelRequestTo;
import com.example.publicator.dto.LabelResponseTo;
import com.example.publicator.exception.NotFoundException;
import com.example.publicator.mapper.LabelListMapper;
import com.example.publicator.mapper.LabelMapper;
import com.example.publicator.model.Label;
import com.example.publicator.repository.LabelRepository;
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

@Service
@Validated
public class LabelService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    LabelListMapper labelListMapper;
    @Autowired
    //LabelDao labelDao;
    LabelRepository labelRepository;

    public LabelResponseTo create(@Valid LabelRequestTo labelRequestTo){
        return labelMapper.labelToLabelResponse(labelRepository.save(labelMapper.labelRequestToLabel(labelRequestTo)));
    }

    public List<LabelResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        Page<Label> res = labelRepository.findAll(p);
        return labelListMapper.toLabelResponseList(res.toList());
    }

    public LabelResponseTo read(@Min(0) int id) throws NotFoundException{
        if(labelRepository.existsById(id)){
            LabelResponseTo label = labelMapper.labelToLabelResponse(labelRepository.getReferenceById(id));
            return label;
        }
        else
            throw new NotFoundException("Label not found",404);
    }

    public LabelResponseTo update(@Valid LabelRequestTo labelRequestTo, @Min(0) int id) throws NotFoundException{
        if(labelRepository.existsById(id)){
            Label label = labelMapper.labelRequestToLabel(labelRequestTo);
            label.setId(id);
            return labelMapper.labelToLabelResponse(labelRepository.save(label));
        }
        else
            throw new NotFoundException("Label not found",404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(labelRepository.existsById(id)){
            labelRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Label not found",404);
    }



}
