package com.poluectov.rvlab1.repository.inmemory;

import com.poluectov.rvlab1.dto.issue.IssueRequestTo;
import com.poluectov.rvlab1.model.Issue;
import com.poluectov.rvlab1.repository.IssueRepository;
import com.poluectov.rvlab1.repository.MarkerRepository;
import com.poluectov.rvlab1.repository.UserRepository;
import com.poluectov.rvlab1.utils.dtoconverter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class InMemoryIssueRepository extends InMemoryRepository<Issue, IssueRequestTo> implements IssueRepository {

    private final UserRepository userRepository;
    private final MarkerRepository markerRepository;

    public InMemoryIssueRepository(DtoConverter<IssueRequestTo, Issue> convert,
                                   UserRepository userRepository,
                                   MarkerRepository markerRepository) {

        super(convert);
        this.userRepository = userRepository;
        this.markerRepository = markerRepository;
    }

    @Override
    public Issue save(IssueRequestTo issueRequestTo) {
        //if there is no such user
        if (userRepository.find(issueRequestTo.getUserId()) == null){
            return null;
        }
        //if there is no such marker
        if (markerRepository.findAll(issueRequestTo.getMarkers()).size() != issueRequestTo.getMarkers().size()){
            return null;
        }

        return super.save(issueRequestTo);
    }
}
