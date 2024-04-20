package by.bsuir.publicator.service.issue.impl;

import by.bsuir.publicator.bean.Editor;
import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.dto.IssueRequestTo;
import by.bsuir.publicator.dto.IssueResponseTo;
import by.bsuir.publicator.exception.DuplicateEntityException;
import by.bsuir.publicator.exception.EntityNotFoundException;
import by.bsuir.publicator.repository.editor.EditorRepository;
import by.bsuir.publicator.repository.issue.IssueRepository;
import by.bsuir.publicator.service.issue.IIssueService;
import by.bsuir.publicator.util.converter.issue.IssueConverter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
