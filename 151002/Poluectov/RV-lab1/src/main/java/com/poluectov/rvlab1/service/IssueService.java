package com.poluectov.rvlab1.service;

import com.poluectov.rvlab1.dto.issue.IssueRequestTo;
import com.poluectov.rvlab1.dto.issue.IssueResponseTo;
import com.poluectov.rvlab1.model.Issue;
import com.poluectov.rvlab1.repository.IssueRepository;
import com.poluectov.rvlab1.utils.dtoconverter.MarkerResponseDtoConverter;
import com.poluectov.rvlab1.utils.dtoconverter.UserResponseDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class IssueService extends CommonRestService<Issue, IssueRequestTo, IssueResponseTo> {

    UserResponseDtoConverter userResponseDtoConverter;
    MarkerResponseDtoConverter markerResponseDtoConverter;
    public IssueService(IssueRepository repository,
                        UserResponseDtoConverter userResponseDtoConverter,
                        MarkerResponseDtoConverter markerResponseDtoConverter) {
        super(repository);
        this.userResponseDtoConverter = userResponseDtoConverter;
        this.markerResponseDtoConverter = markerResponseDtoConverter;
    }

    @Override
    IssueResponseTo mapResponseTo(Issue message) {
        return IssueResponseTo.builder()
                .id(message.getId())
                .title(message.getTitle())
                .user(userResponseDtoConverter.convert(message.getUser()))
                .content(message.getContent())
                .created(message.getCreated())
                .modified(message.getModified())
                .markers(message.getMarkers().stream().map(markerResponseDtoConverter::convert).toList())
                .build();
    }
}
