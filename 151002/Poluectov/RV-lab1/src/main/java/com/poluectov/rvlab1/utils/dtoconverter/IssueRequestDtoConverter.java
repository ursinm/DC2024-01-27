package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.issue.IssueRequestTo;
import com.poluectov.rvlab1.model.Issue;
import com.poluectov.rvlab1.repository.MarkerRepository;
import com.poluectov.rvlab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IssueRequestDtoConverter implements DtoConverter<IssueRequestTo, Issue> {

    private UserRepository userRepository;

    private MarkerRepository markerRepository;

    @Override
    public Issue convert(IssueRequestTo issue) {
        return Issue.builder()

                .id(issue.getId())
                .user(userRepository.find(issue.getUserId()))
                .title(issue.getTitle())
                .content(issue.getContent())
                .created(issue.getCreated())
                .modified(issue.getModified())

                .markers(markerRepository.findAll(issue.getMarkers()))

                .build();
    }
}
