package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.issue.IssueResponseTo;
import com.poluectov.rvlab1.model.Issue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IssueResponseDtoConverter implements DtoConverter<Issue, IssueResponseTo> {


    UserResponseDtoConverter userResponseDtoConverter;
    MarkerResponseDtoConverter markerResponseDtoConverter;
    @Override
    public IssueResponseTo convert(Issue issue) {
        return IssueResponseTo.builder()
               .id(issue.getId())
               .user(userResponseDtoConverter.convert(issue.getUser()))
               .title(issue.getTitle())
               .content(issue.getContent())
               .created(issue.getCreated())
               .modified(issue.getModified())
               .markers(issue.getMarkers().stream().map(markerResponseDtoConverter::convert).toList())
               .build();
   }
}
