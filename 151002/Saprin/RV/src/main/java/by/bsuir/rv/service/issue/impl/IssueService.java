package by.bsuir.rv.service.issue.impl;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.IssueRequestTo;
import by.bsuir.rv.dto.IssueResponseTo;
import by.bsuir.rv.exception.EntititesNotFoundException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.editor.EditorRepositoryMemory;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.repository.issue.IssueRepositoryMemory;
import by.bsuir.rv.repository.sticker.StickerRepositoryMemory;
import by.bsuir.rv.service.issue.IIssueService;
import by.bsuir.rv.util.converter.issue.IssueConverter;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IssueService implements IIssueService {

    private final IssueConverter issueConverter;
    private final IssueRepositoryMemory issueRepository;
    private final EditorRepositoryMemory editorRepository;
    private final StickerRepositoryMemory stickerRepository;

    private final String ENTITY_NAME = "issue";


    public IssueService(IssueConverter issueConverter, IssueRepositoryMemory issueRepository, EditorRepositoryMemory editorRepository, StickerRepositoryMemory stickerRepository) {
        this.issueConverter = issueConverter;
        this.issueRepository = issueRepository;
        this.editorRepository = editorRepository;
        this.stickerRepository = stickerRepository;
    }

    @Override
    public List<IssueResponseTo> getIssues() throws EntititesNotFoundException {
        List<Issue> issues = issueRepository.findAll();
        List<IssueResponseTo> result = new ArrayList<>();

        for (Issue issue : issues) {
            result.add(issueConverter.convertToResponse(issue));
        }
        return result;
    }

    @Override
    public IssueResponseTo getIssueById(BigInteger id) throws EntityNotFoundException {
        Issue issue;
        try {
            issue = issueRepository.findById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }

        return issueConverter.convertToResponse(issue);
    }

    @Override
    public IssueResponseTo addIssue(IssueRequestTo issue) throws EntityNotFoundException {
        Issue issueEntity = issueConverter.convertToEntity(issue);
        Issue savedIssue = issueRepository.save(issueEntity);
        return this.getIssueById(savedIssue.getId());
    }

    @Override
    public void deleteIssue(BigInteger id) throws EntityNotFoundException {
        try {
            issueRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }

    @Override
    public IssueResponseTo updateIssue(IssueRequestTo issue) throws EntityNotFoundException, EntititesNotFoundException {
        try {
            issueRepository.findById(issue.getId());
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, issue.getId());
        }
        Issue issueEntity = issueConverter.convertToEntity(issue);
        Issue savedIssue = issueRepository.save(issueEntity);
        return this.getIssueById(savedIssue.getId());
    }
}
