package com.example.lab2.Service;

import com.example.lab2.DTO.StoryRequestTo;
import com.example.lab2.DTO.StoryResponseTo;
import com.example.lab2.Exception.DuplicateException;
import com.example.lab2.Exception.NotFoundException;
import com.example.lab2.Mapper.StoryListMapper;
import com.example.lab2.Mapper.StoryMapper;
import com.example.lab2.Model.Story;
import com.example.lab2.Repository.EditorRepository;
import com.example.lab2.Repository.StoryRepository;
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
public class StoryService {
    @Autowired
    StoryMapper storyMapper;
    @Autowired
    StoryListMapper storyListMapper;
    @Autowired
    StoryRepository storyRepository;
    //StoryDao storyDao;
    @Autowired
    EditorRepository editorRepository;

    public StoryResponseTo create(@Valid StoryRequestTo storyRequestTo){
        Story story = storyMapper.storyRequestToStory(storyRequestTo);
        if(storyRepository.existsByTitle(story.getTitle())){
            throw new DuplicateException("Title duplication", 403);
        }
        if(storyRequestTo.getEditorId() != 0){
            story.setEditor(editorRepository.findById(storyRequestTo.getEditorId()).orElseThrow(() -> new NotFoundException("Editor not found", 404)));
        }
        return storyMapper.storyToStoryResponse(storyRepository.save(story));
    }

    public List<StoryResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction) {
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        Page<Story> res = storyRepository.findAll(p);
        return storyListMapper.toStoryResponseList(res.toList());
    }

    public StoryResponseTo read(@Min(0) int id) throws NotFoundException {
        if(storyRepository.existsById(id)){
            StoryResponseTo story = storyMapper.storyToStoryResponse(storyRepository.getReferenceById(id));
            return story;
        }
        else
            throw new NotFoundException("Story not found", 404);
    }

    public StoryResponseTo update(@Valid StoryRequestTo storyRequestTo, @Min(0) int id) throws NotFoundException {
        if(storyRepository.existsById(id)){
            Story story = storyMapper.storyRequestToStory(storyRequestTo);
            story.setId(id);
            if(storyRequestTo.getEditorId() != 0){
                story.setEditor(editorRepository.findById(storyRequestTo.getEditorId()).orElseThrow(() -> new NotFoundException("Editor not found", 404)));
            }
            return storyMapper.storyToStoryResponse(storyRepository.save(story));
        }
        else
            throw new NotFoundException("Story not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException{
        if(storyRepository.existsById(id)){
            storyRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Story not found", 404);
    }

}
