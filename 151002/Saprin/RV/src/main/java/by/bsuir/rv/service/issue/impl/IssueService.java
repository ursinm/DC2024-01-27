package by.bsuir.rv.service.issue.impl;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.exception.DuplicateEntityException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.editor.EditorRepository;
import by.bsuir.rv.repository.issue.IssueRepository;
import by.bsuir.rv.service.issue.IIssueService;
import by.bsuir.rv.util.converter.issue.IssueConverter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Service
public class IssueService implements IIssueService {

    private final IssueConverter issueConverter;
    private final IssueRepository issueRepository;
    private final EditorRepository editorRepository;

    private final String ENTITY_NAME = "issue";


    public IssueService(IssueConverter issueConverter, IssueRepository issueRepository, EditorRepository editorRepository) {
        this.issueConverter = issueConverter;
        this.issueRepository = issueRepository;
        this.editorRepository = editorRepository;
    }

    @Override
    public List<IssueResponseTo> getIssues() {
        List<Issue> issues = issueRepository.findAll();
        List<IssueResponseTo> result = new ArrayList<>();

        for (Issue issue : issues) {
            result.add(issueConverter.convertToResponse(issue));
        }
        return result;
    }

    @Override
    public IssueResponseTo getIssueById(BigInteger id) throws EntityNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return issueConverter.convertToResponse(issue.get());
    }

    @Override
    public IssueResponseTo addIssue(IssueRequestTo issue) throws EntityNotFoundException, DuplicateEntityException {
        Optional<Editor> editor = editorRepository.findById(issue.getEditorId());
        if (editor.isEmpty()) {
            throw new EntityNotFoundException("Editor", issue.getEditorId());
        }
        if (issue.getCreated() == null) {
            issue.setCreated(new Date());
        }
        if (issue.getModified() == null) {
            issue.setModified(new Date());
        }
        if (isNumeric(issue.getContent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "iss_content should be a string, not a number.");
        }
        Issue issueEntity = issueConverter.convertToEntity(issue, editor.get());
        try {
            Issue savedIssue = issueRepository.save(issueEntity);
            return issueConverter.convertToResponse(savedIssue);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "iss_content");
        }
    }

    @Override
    public void deleteIssue(BigInteger id) throws EntityNotFoundException {
        if (issueRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        issueRepository.deleteById(id);
    }

    @Override
    public IssueResponseTo updateIssue(IssueRequestTo issue) throws EntityNotFoundException, DuplicateEntityException {
        if (issueRepository.findById(issue.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, issue.getId());
        }
        Optional<Editor> editor = editorRepository.findById(issue.getEditorId());
        if (editor.isEmpty()) {
            throw new EntityNotFoundException("Editor", issue.getEditorId());
        }
        if (issue.getCreated() == null) {
            issue.setCreated(new Date());
        }
        if (issue.getModified() == null) {
            issue.setModified(new Date());
        }
        Issue issueEntity = issueConverter.convertToEntity(issue, editor.get());
        try {
            Issue savedIssue = issueRepository.save(issueEntity);
            return issueConverter.convertToResponse(savedIssue);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "iss_content");
        }
    }
}
