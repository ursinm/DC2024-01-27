package by.bsuir.publisher.service;

import by.bsuir.publisher.exception.ResourceNotFoundException;
import by.bsuir.publisher.model.dto.request.IssueRequestDto;
import by.bsuir.publisher.model.dto.response.IssueResponseDto;
import by.bsuir.publisher.model.entity.Issue;
import by.bsuir.publisher.model.mapper.IssueMapper;
import by.bsuir.publisher.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    public IssueResponseDto create(IssueRequestDto dto) {
        Issue issue = issueMapper.toEntity(dto);
        return issueMapper.toDto(issueRepository.save(issue));
    }

    public IssueResponseDto update(IssueRequestDto dto) {
        Issue issue = issueRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Issue with id %s not found", dto.id())));
        final Issue updatedIssue = issueMapper.partialUpdate(dto, issue);
        return issueMapper.toDto(issueRepository.save(updatedIssue));
    }

    public void delete(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Issue with id %s not found", id)));
        issueRepository.delete(issue);
    }

    @Transactional(readOnly = true)
    public IssueResponseDto get(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Issue with id %s not found", id)));
        return issueMapper.toDto(issue);
    }

    @Transactional(readOnly = true)
    public List<IssueResponseDto> getAll() {
        return issueMapper.toDto(issueRepository.findAll());
    }

}