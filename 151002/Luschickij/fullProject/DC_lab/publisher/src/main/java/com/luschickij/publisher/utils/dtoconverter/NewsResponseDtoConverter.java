package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.news.NewsResponseTo;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import com.luschickij.publisher.model.News;
import com.luschickij.publisher.model.Label;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
@AllArgsConstructor
public class NewsResponseDtoConverter implements DtoConverter<News, NewsResponseTo> {


    CreatorResponseDtoConverter creatorResponseDtoConverter;
    LabelResponseDtoConverter labelResponseDtoConverter;

    @Override
    public NewsResponseTo convert(News news) {
        List<Label> labelList = news.getLabels();
        List<Long> labels = null;
        if (labelList != null) {
            labels = labelList.stream().map(Label::getId).toList();
        }

        return NewsResponseTo.builder()
                .id(news.getId())
                .creatorId(news.getCreator().getId())
                .title(news.getTitle())
                .content(news.getContent())
                .created(news.getCreated())
                .modified(news.getModified())
                .labels(labels)
                .build();
    }
}
