package by.bsuir.messageapp.service.mapper;

import by.bsuir.messageapp.dao.repository.CreatorRepository;
import by.bsuir.messageapp.dao.repository.StoryRepository;
import by.bsuir.messageapp.model.entity.Creator;
import by.bsuir.messageapp.model.entity.Story;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final StoryRepository storyRepository;
    private final CreatorRepository creatorRepository;

    @Named("storyRefFromStoryId")
    public Story takeStoryRefFromStoryRequestTo(Long storyId) {
        Optional<Story> story = storyRepository.findById(storyId);

        return story.orElseThrow();
    }

    @Named("creatorRefFromCreatorId")
    public Creator takeCreatorRefFromCreatorRequestTo(Long creatorId) {
        Optional<Creator> creator = creatorRepository.findById(creatorId);

        return creator.orElseThrow();
    }
}
