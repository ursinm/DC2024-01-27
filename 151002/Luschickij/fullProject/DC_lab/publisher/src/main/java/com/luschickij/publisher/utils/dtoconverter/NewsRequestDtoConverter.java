package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.news.NewsRequestTo;
import com.luschickij.publisher.model.News;
import com.luschickij.publisher.model.Label;
import com.luschickij.publisher.model.Creator;
import com.luschickij.publisher.repository.LabelRepository;
import com.luschickij.publisher.repository.CreatorRepository;
import com.luschickij.publisher.repository.exception.EntityNotFoundException;
import com.luschickij.publisher.repository.jpa.JpaLabelRepository;
import com.luschickij.publisher.repository.jpa.JpaCreatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class NewsRequestDtoConverter implements DtoConverter<NewsRequestTo, News> {

    private JpaCreatorRepository creatorRepository;

    private JpaLabelRepository labelRepository;

    @Override
    public News convert(NewsRequestTo news) {
        if (news == null) {
            return null;
        }
        //check if creator and labels exists
        Creator creator = creatorRepository
                .findById(news.getCreatorId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Creator with id " + news.getCreatorId() + " not found"
                        )
                );
        List<Label> labelList = null;
        //check if labels exists
        if (news.getLabels() != null) {
            labelList = labelRepository.findByIdIn(news.getLabels());
            //if some labels are not found
            if (labelList.size() != news.getLabels().size()) {
                List<Long> ids = findMissingIds(labelList, news.getLabels());
                throw new EntityNotFoundException("Labels with ids " + getMissingIdsPost(ids) + " not found");
            }
        }


        return News.builder()
                .id(news.getId())
                .creator(creator)
                .title(news.getTitle())
                .content(news.getContent())
                .created(news.getCreated())
                .modified(news.getModified())
                .labels(labelList)
                .build();
    }

    private List<Long> findMissingIds(List<Label> labelList, List<Long> idList) {
        List<Long> allLabelIds = labelList.stream()
                .map(Label::getId)
                .toList();

        // Фильтруем список idList, оставляя только те, которых нет в allLabelIds
        return idList.stream()
                .filter(id -> !allLabelIds.contains(id))
                .collect(Collectors.toList());
    }

    private String getMissingIdsPost(List<Long> ids) {
        return ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
