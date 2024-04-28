package by.bsuir.vladislavmatsiushenko.service;

import by.bsuir.vladislavmatsiushenko.exception.DuplicateException;
import by.bsuir.vladislavmatsiushenko.dto.IssueRequestTo;
import by.bsuir.vladislavmatsiushenko.dto.IssueResponseTo;
import by.bsuir.vladislavmatsiushenko.exception.NotFoundException;
import by.bsuir.vladislavmatsiushenko.mapper.IssueListMapper;
import by.bsuir.vladislavmatsiushenko.mapper.IssueMapper;
import by.bsuir.vladislavmatsiushenko.model.Issue;
import by.bsuir.vladislavmatsiushenko.repository.UserRepository;
import by.bsuir.vladislavmatsiushenko.repository.IssueRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    IssueListMapper issueListMapper;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    UserRepository userRepository;

    public IssueResponseTo create(@Valid IssueRequestTo issueRequestTo) {
        Issue issue = issueMapper.issueRequestToIssue(issueRequestTo);
        if (issueRepository.existsByTitle(issue.getTitle())) {
            throw new DuplicateException("Title duplication", 403);
        }
        if (issueRequestTo.getUserId() != 0) {
            issue.setUser(userRepository.findById(issueRequestTo.getUserId()).orElseThrow(() -> new NotFoundException("User not found", 404)));
        }

        return issueMapper.issueToIssueResponse(issueRepository.save(issue));
    }

    public IssueResponseTo read(@Min(0) int id) throws NotFoundException {
        if (issueRepository.existsById(id)) {
            IssueResponseTo issue = issueMapper.issueToIssueResponse(issueRepository.getReferenceById(id));

            return issue;
        }

        throw new NotFoundException("Issue not found", 404);
    }

    public List<IssueResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction) {
        Pageable p;
        if (direction != null && direction.equals("asc")) {
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).ascending());
        } else {
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).descending());
        }
        Page<Issue> res = issueRepository.findAll(p);

        return issueListMapper.toIssueResponseList(res.toList());
    }

    public IssueResponseTo update(@Valid IssueRequestTo issueRequestTo, @Min(0) int id) throws NotFoundException {
        if (issueRepository.existsById(id)) {
            Issue issue = issueMapper.issueRequestToIssue(issueRequestTo);
            issue.setId(id);
            if (issueRequestTo.getUserId() != 0) {
                issue.setUser(userRepository.findById(issueRequestTo.getUserId()).orElseThrow(() -> new NotFoundException("User not found", 404)));
            }

            return issueMapper.issueToIssueResponse(issueRepository.save(issue));
        }

        throw new NotFoundException("Issue not found", 404);
    }

    public boolean delete(@Min(0) int id) throws NotFoundException {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);

            return true;
        }

        throw new NotFoundException("Issue not found", 404);
    }
}
