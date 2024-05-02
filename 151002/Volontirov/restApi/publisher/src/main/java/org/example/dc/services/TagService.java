package org.example.dc.services;

import org.example.dc.model.Tag;
import org.example.dc.model.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> getTags();

    TagDto getTagById(int id);

    boolean delete(int id) throws Exception;

    TagDto updateTag(TagDto tagDto);

    TagDto createTag(TagDto tagDto);
}
