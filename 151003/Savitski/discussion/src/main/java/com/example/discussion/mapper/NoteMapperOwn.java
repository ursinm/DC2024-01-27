package com.example.discussion.mapper;

import com.example.discussion.model.entity.Note;
import com.example.discussion.model.response.NoteResponseTo;
import org.springframework.stereotype.Service;

@Service
public class NoteMapperOwn {
    public NoteResponseTo entityToResponse(Note entity) {
        return NoteResponseTo.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .storyId(entity.getStoryId())
                .build();
    }
}
