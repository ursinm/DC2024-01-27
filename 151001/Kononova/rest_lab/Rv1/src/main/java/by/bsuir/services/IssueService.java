package by.bsuir.services;

import by.bsuir.dto.IssueRequestTo;
import by.bsuir.dto.IssueResponseTo;
import by.bsuir.entities.Issue;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.DuplicationException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.IssueListMapper;
import by.bsuir.mapper.IssueMapper;
import by.bsuir.repository.IssueRepository;
import by.bsuir.repository.UserRepository;
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
import java.util.Optional;

@Service
@Validated
public class IssueService {

    @Autowired
    IssueMapper issueMapper;
    @Autowired
    IssueRepository issueDao;
    @Autowired
    IssueListMapper issueListMapper;
    @Autowired
    UserRepository userRepository;

    public IssueResponseTo getIssueById(@Min(0) Long id) throws NotFoundException{
        Optional<Issue> issue = issueDao.findById(id);
        return issue.map(value -> issueMapper.issueToIssueResponse(value)).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L));
    }

    public List<IssueResponseTo> getIssues(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Issue> issues = issueDao.findAll(pageable);
        return issueListMapper.toIssueResponseList(issues.toList());
    }

    public IssueResponseTo saveIssue(@Valid IssueRequestTo issue) throws DuplicationException {
        Issue issueToSave = issueMapper.issueRequestToIssue(issue);
        if (issueDao.existsByTitle(issueToSave.getTitle())) {
            throw new DuplicationException("Title duplication", 40005L);
        }
        if (issue.getUserId() != null) {
            issueToSave.setUser(userRepository.findById(issue.getUserId()).orElseThrow(() -> new NotFoundException("User not found!", 40004L)));
        }
        return issueMapper.issueToIssueResponse(issueDao.save(issueToSave));
    }

    public IssueResponseTo updateIssue(@Valid IssueRequestTo issue) throws UpdateException {
        Issue issueToUpdate = issueMapper.issueRequestToIssue(issue);
        if (!issueDao.existsById(issue.getId())) {
            throw new UpdateException("Message not found!", 40004L);
        } else {
            if (issue.getUserId() != null) {
                issueToUpdate.setUser(userRepository.findById(issue.getUserId()).orElseThrow(() -> new NotFoundException("User not found!", 40004L)));
            }
            return issueMapper.issueToIssueResponse(issueDao.save(issueToUpdate));
        }
    }

    public List<IssueResponseTo> getIssueByCriteria(List<String> stickerName, List<Long> stickerId, String userLogin, String title, String content) {
        return issueListMapper.toIssueResponseList(issueDao.findIssuesByParams( userLogin, title, content));
    }

    public void deleteIssue(@Min(0) Long id) throws DeleteException {
        if (!issueDao.existsById(id)) {
            throw new DeleteException("Issue not found!", 40004L);
        } else {
            issueDao.deleteById(id);
        }
    }
}
