package com.luschickij.publisher.service;

import com.luschickij.publisher.dto.news.NewsRequestTo;
import com.luschickij.publisher.dto.news.NewsResponseTo;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import com.luschickij.publisher.model.News;
import com.luschickij.publisher.model.Label;
import com.luschickij.publisher.repository.NewsRepository;
import com.luschickij.publisher.repository.jpa.JpaNewsRepository;
import com.luschickij.publisher.utils.dtoconverter.NewsRequestDtoConverter;
import com.luschickij.publisher.utils.dtoconverter.LabelResponseDtoConverter;
import com.luschickij.publisher.utils.dtoconverter.CreatorResponseDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Component
public class NewsService extends CommonRestService<News, NewsRequestTo, NewsResponseTo, Long> {

    public NewsService(NewsRepository repository,
                        NewsRequestDtoConverter dtoConverter) {
        super(repository, dtoConverter);
    }

    @Override
    protected Optional<NewsResponseTo> mapResponseTo(News post) {
        List<Label> labelList = post.getLabels();
        List<Long> labels = null;
        if (labelList != null) {
            labels = labelList.stream().map(Label::getId).toList();
        }
        return Optional.ofNullable(NewsResponseTo.builder()
                .id(post.getId())
                .title(post.getTitle())
                .creatorId(post.getCreator().getId())
                .content(post.getContent())
                .created(post.getCreated())
                .modified(post.getModified())
                .labels(labels)
                .build());
    }

    @Override
    protected void update(News one, News found) {
        one.setCreator(found.getCreator());

        one.setTitle(found.getTitle());

        one.setContent(found.getContent());
        one.setCreated(found.getCreated());
        one.setModified(found.getModified());

        one.setLabels(found.getLabels());
    }
}
