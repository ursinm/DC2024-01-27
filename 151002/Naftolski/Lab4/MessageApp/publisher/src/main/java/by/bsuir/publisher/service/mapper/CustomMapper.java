package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dao.repository.CreatorRepository;
import by.bsuir.publisher.dao.repository.StoryRepository;
import by.bsuir.publisher.model.entity.Creator;
import by.bsuir.publisher.model.entity.Story;
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
