package com.example.lab1.Service;

import com.example.lab1.DAO.IssueDao;
import com.example.lab1.DTO.IssueRequestTo;
import com.example.lab1.DTO.IssueResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.IssueListMapper;
import com.example.lab1.Mapper.IssueMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
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
    IssueDao issueDao;

    public IssueResponseTo read(@Min(0) int id) throws NotFoundException {
        IssueResponseTo issue = issueMapper.issueToIssueResponse(issueDao.read(id));
        if(issue != null)
            return issue;
        else
            throw new NotFoundException("Issue not found", 404);
    }
    public List<IssueResponseTo> readAll() {
        return issueListMapper.toIssueResponseList(issueDao.readAll());
    }
    public IssueResponseTo create(@Valid IssueRequestTo issueRequestTo){
        return issueMapper.issueToIssueResponse(issueDao.create(issueMapper.issueReguestToIssue(issueRequestTo)));
    }
    public IssueResponseTo update(@Valid IssueRequestTo issueRequestTo, @Min(0) int id) throws NotFoundException {
        IssueResponseTo issue = issueMapper.issueToIssueResponse(issueDao.update(issueMapper.issueReguestToIssue(issueRequestTo),id));
        if(issue != null)
            return issue;
        else
            throw new NotFoundException("Issue not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException{
        boolean isDeleted = issueDao.delete(id);
        if(isDeleted)
            return true;
        else
            throw new NotFoundException("Issue not found", 404);
    }

}
