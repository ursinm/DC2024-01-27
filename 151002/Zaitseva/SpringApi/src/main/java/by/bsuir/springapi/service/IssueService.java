package by.bsuir.springapi.service;

import by.bsuir.springapi.dao.impl.IssueRepository;
import by.bsuir.springapi.model.entity.Issue;
import by.bsuir.springapi.model.request.IssueRequestTo;
import by.bsuir.springapi.model.response.IssueResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.IssueMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class IssueService implements RestService<IssueRequestTo, IssueResponseTo> {
    private final IssueRepository issueRepository;

    private final IssueMapper issueMapper;

    @Override
    public List<IssueResponseTo> findAll() {
        return issueMapper.getListResponseTo(issueRepository.getAll());
    }

    @Override
    public IssueResponseTo findById(Long id) {
        return issueMapper.getResponseTo(issueRepository
                .getBy(id)
                .orElseThrow(() -> newsNotFoundException(id)));
    }

    @Override
    public IssueResponseTo create(IssueRequestTo newsTo) {
        Issue issue = issueMapper.getNews(newsTo);
        issue.setCreated(LocalDateTime.now());
        issue.setModified(issue.getCreated());
        return issueRepository
                .save(issue)
                .map(issueMapper::getResponseTo)
                .orElseThrow(IssueService::newsStateException);
    }

    @Override
    public IssueResponseTo update(IssueRequestTo newsTo) {
        issueRepository
                .getBy(issueMapper.getNews(newsTo).getId())
                .orElseThrow(() -> newsNotFoundException(issueMapper.getNews(newsTo).getId()));
        return issueRepository
                .update(issueMapper.getNews(newsTo))
                .map(issueMapper::getResponseTo)
                .orElseThrow(IssueService::newsStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!issueRepository.removeById(id)) {
            throw newsNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException newsNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find news with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 53);
    }

    private static ResourceStateException newsStateException() {
        return new ResourceStateException("Failed to create/update news with specified credentials", HttpStatus.CONFLICT.value() * 100 + 54);
    }
}
