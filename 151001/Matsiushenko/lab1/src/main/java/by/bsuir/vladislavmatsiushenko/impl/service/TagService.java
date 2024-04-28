package by.bsuir.vladislavmatsiushenko.impl.service;


import by.bsuir.vladislavmatsiushenko.api.Service;
import by.bsuir.vladislavmatsiushenko.api.TagMapper;
import by.bsuir.vladislavmatsiushenko.impl.bean.Tag;
import by.bsuir.vladislavmatsiushenko.impl.dto.*;
import by.bsuir.vladislavmatsiushenko.impl.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagService implements Service<TagResponseTo, TagRequestTo> {
    @Autowired
    private TagRepository tagRepository;

    public TagService() {}

    public List<TagResponseTo> getAll() {
        List<Tag> tagList = tagRepository.getAll();
        List<TagResponseTo> resultList = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            resultList.add(TagMapper.INSTANCE.TagToTagResponseTo(tagList.get(i)));
        }

        return resultList;
    }

    public TagResponseTo update(TagRequestTo updatingTag) {
        Tag tag = TagMapper.INSTANCE.TagRequestToToTag(updatingTag);
        if (validateTag(tag)) {
            boolean result = tagRepository.update(tag);
            TagResponseTo responseTo = result ? TagMapper.INSTANCE.TagToTagResponseTo(tag) : null;

            return responseTo;
        }

        return new TagResponseTo();
    }

    public TagResponseTo get(long id) {
        return TagMapper.INSTANCE.TagToTagResponseTo(tagRepository.get(id));
    }

    public TagResponseTo delete(long id) {
        return TagMapper.INSTANCE.TagToTagResponseTo(tagRepository.delete(id));
    }

    public TagResponseTo add(TagRequestTo tagRequestTo) {
        Tag tag = TagMapper.INSTANCE.TagRequestToToTag(tagRequestTo);

        return TagMapper.INSTANCE.TagToTagResponseTo(tagRepository.insert(tag));
    }

    private boolean validateTag(Tag tag) {
        String content = tag.getName();

        return content.length() >= 2 && content.length() <= 2048;
    }
}
