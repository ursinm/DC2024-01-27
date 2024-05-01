package by.bsuir.restapi.service;

import by.bsuir.restapi.exception.ResourceNotFoundException;
import by.bsuir.restapi.model.dto.request.IssueRequestDto;
import by.bsuir.restapi.model.dto.response.IssueResponseDto;
import by.bsuir.restapi.model.entity.Issue;
import by.bsuir.restapi.model.mapper.IssueMapper;
import by.bsuir.restapi.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    public IssueResponseDto create(IssueRequestDto dto) {
        Issue issue = issueMapper.toEntity(dto);
        Instant now = Instant.now();
        issue.setCreated(now);
        issue.setModified(now);
        return issueMapper.toDto(issueRepository.save(issue));
    }

    public IssueResponseDto update(IssueRequestDto dto) {
        Issue issue = issueRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Issue with id %s not found", dto.id())));
        if (dto.title() != null)
            issue.setTitle(dto.title());
        if (dto.content() != null)
            issue.setContent(dto.content());
        if (dto.authorId() != null)
            issue.setAuthorId(dto.authorId());
        issue.setModified(Instant.now());
        return issueMapper.toDto(issueRepository.save(issue));
    }

    public void delete(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Issue with id %s not found", id)));
        issueRepository.delete(issue);
    }

    public IssueResponseDto get(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Issue with id %s not found", id)));
        return issueMapper.toDto(issue);
    }

    public List<IssueResponseDto> getAll() {
        return issueMapper.toDto(issueRepository.findAll());
    }

}