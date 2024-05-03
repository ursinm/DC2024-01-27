package com.luschickij.DC_lab.service.mapper;

import com.luschickij.DC_lab.dao.repository.CreatorRepository;
import com.luschickij.DC_lab.dao.repository.NewsRepository;
import com.luschickij.DC_lab.model.entity.Creator;
import com.luschickij.DC_lab.model.entity.News;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final NewsRepository newsRepository;
    private final CreatorRepository creatorRepository;

    @Named("newsRefFromNewsId")
    public News newsRefFromNewsId(Long newsId) {
        Optional<News> news = newsRepository.findById(newsId);

//        return newsRepository.getReferenceById(newsId);
        return news.orElseThrow();
    }

    @Named("creatorRefFromCreatorId")
    public Creator creatorRefFromCreatorId(Long creatorId) {
        Optional<Creator> creator = creatorRepository.findById(creatorId);

        return creator.orElseThrow();
    }
}
