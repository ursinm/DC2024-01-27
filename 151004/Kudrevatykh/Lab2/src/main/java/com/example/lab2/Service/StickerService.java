package com.example.lab2.Service;

import com.example.lab2.DTO.StickerRequestTo;
import com.example.lab2.DTO.StickerResponseTo;
import com.example.lab2.Exception.NotFoundException;
import com.example.lab2.Mapper.StickerListMapper;
import com.example.lab2.Mapper.StickerMapper;
import com.example.lab2.Model.Sticker;
import com.example.lab2.Repository.StickerRepository;
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
public class StickerService {
    @Autowired
    StickerMapper stickerMapper;
    @Autowired
    StickerListMapper stickerListMapper;
    @Autowired
    //StickerDao stickerDao;
    StickerRepository stickerRepository;

    public StickerResponseTo create(@Valid StickerRequestTo stickerRequestTo){
        return stickerMapper.stickerToStickerResponse(stickerRepository.save(stickerMapper.stickerRequestToSticker(stickerRequestTo)));
    }

    public List<StickerResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        Page<Sticker> res = stickerRepository.findAll(p);
        return stickerListMapper.toStickerResponseList(res.toList());
    }

    public StickerResponseTo read(@Min(0) int id) throws NotFoundException{
        if(stickerRepository.existsById(id)){
            StickerResponseTo sticker = stickerMapper.stickerToStickerResponse(stickerRepository.getReferenceById(id));
            return sticker;
        }
        else
            throw new NotFoundException("Sticker not found",404);
    }

    public StickerResponseTo update(@Valid StickerRequestTo stickerRequestTo, @Min(0) int id) throws NotFoundException{
        if(stickerRepository.existsById(id)){
            Sticker sticker = stickerMapper.stickerRequestToSticker(stickerRequestTo);
            sticker.setId(id);
            return stickerMapper.stickerToStickerResponse(stickerRepository.save(sticker));
        }
        else
            throw new NotFoundException("Sticker not found",404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(stickerRepository.existsById(id)){
            stickerRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Sticker not found",404);
    }



}
