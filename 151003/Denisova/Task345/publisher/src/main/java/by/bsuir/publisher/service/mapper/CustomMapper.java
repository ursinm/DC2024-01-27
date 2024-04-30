package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dao.EditorRepository;
import by.bsuir.publisher.dao.StoryRepository;
import by.bsuir.publisher.model.entity.Editor;
import by.bsuir.publisher.model.entity.Story;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final StoryRepository storyRepository;
    private final EditorRepository editorRepository;

    @Named("newsIdToNewsRef")
    public Story newsIdToNewsRef(Long newsId) {
        return storyRepository.getReferenceById(newsId);
    }

    @Named("editorIdToEditorRef")
    public Editor editorIdToEditorRef(Long editorId) {
        return editorRepository.getReferenceById(editorId);
    }
}
