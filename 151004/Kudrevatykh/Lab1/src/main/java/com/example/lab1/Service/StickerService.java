package com.example.lab1.Service;

import com.example.lab1.DAO.StickerDao;
import com.example.lab1.DTO.StickerRequestTo;
import com.example.lab1.DTO.StickerResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.StickerListMapper;
import com.example.lab1.Mapper.StickerMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
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
    StickerDao stickerDao;

    public StickerResponseTo read(@Min(0) int id) throws NotFoundException{
        StickerResponseTo sticker = stickerMapper.stickerToStickerResponse(stickerDao.read(id));
        if(sticker != null)
            return stickerMapper.stickerToStickerResponse(stickerDao.read(id));
        else
            throw new NotFoundException("Sticker not found",404);
    }
    public List<StickerResponseTo> readAll() {
        return stickerListMapper.toStickerResponseList(stickerDao.readAll());
    }

    public StickerResponseTo create(@Valid StickerRequestTo stickerRequestTo){
        return stickerMapper.stickerToStickerResponse(stickerDao.create(stickerMapper.stickerRequestToSticker(stickerRequestTo)));
    }

    public StickerResponseTo update(@Valid StickerRequestTo stickerRequestTo, @Min(0) int id) throws NotFoundException{
        StickerResponseTo sticker = stickerMapper.stickerToStickerResponse(stickerDao.update(stickerMapper.stickerRequestToSticker(stickerRequestTo),id));
        if(sticker != null)
            return sticker;
        else throw new NotFoundException("Sticker not found",404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(stickerDao.delete(id))
            return true;
        else
            throw new NotFoundException("Sticker not found",404);
    }



}
