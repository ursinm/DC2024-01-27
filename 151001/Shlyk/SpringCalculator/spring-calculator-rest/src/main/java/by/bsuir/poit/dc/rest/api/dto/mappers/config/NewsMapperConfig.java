package by.bsuir.poit.dc.rest.api.dto.mappers.config;

import by.bsuir.poit.dc.rest.dao.LabelRepository;
import by.bsuir.poit.dc.rest.dao.NewsRepository;
import by.bsuir.poit.dc.rest.dao.UserRepository;
import by.bsuir.poit.dc.rest.model.Label;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MapperConfig;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author Paval Shlyk
 * @since 05/02/2024
 */
@MapperConfig
@Component
@RequiredArgsConstructor
public class NewsMapperConfig {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;

    @Named("getUserRef")
    public User getUserRef(long userId) {
	return userRepository.getReferenceById(userId);
    }

    @Named("getNewsRef")
    public News getNewsRef(long newsId) {
	return newsRepository.getReferenceById(newsId);
    }

    @Named("getLabelRef")
    public Label getLabelRef(long labelId) {
	return labelRepository.getReferenceById(labelId);
    }
}
