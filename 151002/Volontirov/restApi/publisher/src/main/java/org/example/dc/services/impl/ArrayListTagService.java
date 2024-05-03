package org.example.dc.services.impl;

import org.example.dc.model.Tag;
import org.example.dc.model.TagDto;
import org.example.dc.services.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTagService implements TagService {
    private static int id = 1;
    private List<TagDto> tags = new ArrayList<>();
    @Override
    public List<TagDto> getTags() {
        return tags;
    }

    @Override
    public TagDto getTagById(int id) {
        return tags.stream()
                .filter(tag -> tag.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean delete(int id) throws Exception {
        TagDto tag = tags.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        tags.remove(tag);
        return true;
    }

    @Override
    public TagDto updateTag(TagDto tagDto) {
        TagDto tag = tags.stream()
                .filter(t -> t.getId() == tagDto.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        tag.setName(tagDto.getName());
        return tagDto;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        tagDto.setId(id++);
        tags.add(tagDto);
        return tagDto;
    }
}
