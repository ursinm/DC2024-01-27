package by.bsuir.dc.rest_basics.dal.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Story;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StoryDao extends MemoryRepository<Story> {

    @Override
    public Optional<Story> update(Story story) {
        Long id = story.getId();
        Story memoryStory = map.get(id);
        if (story.getAuthorId() != null) {
            memoryStory.setAuthorId(story.getAuthorId());
        }
        if (story.getTitle() != null) {
            memoryStory.setTitle(story.getTitle());
        }
        if (story.getContent() != null) {
            memoryStory.setContent(story.getContent());
        }
        if (story.getCreated() != null) {
            memoryStory.setCreated(story.getCreated());
        }
        if (story.getModified() != null) {
            memoryStory.setModified(story.getModified());
        }

        return Optional.of(memoryStory);
    }

}
