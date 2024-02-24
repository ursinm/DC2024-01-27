package by.bsuir.rv.repository.issue;

import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.repository.IRepository;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IssueRepositoryMemory implements IRepository<Issue> {
    private final Map<BigInteger, Issue> issues = new ConcurrentHashMap<>();
    @Override
    public Issue save(Issue entity) {
        if (entity.getId() == null) {
            entity.setId(BigInteger.valueOf(issues.size() + 1));
        }
        issues.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Issue> findAll() {
        return List.copyOf(issues.values());
    }

    @Override
    public Issue findById(BigInteger id) throws RepositoryException {
        if (!issues.containsKey(id)) {
            throw new RepositoryException("Issue with id " + id + " not found");
        }
        return issues.get(id);
    }

    @Override
    public List<Issue> findAllById(List<BigInteger> ids) throws RepositoryException {
        List<Issue> result = new ArrayList<>();
        List<BigInteger> notFoundIds = new ArrayList<>();
        for (BigInteger id : ids) {
            if (issues.containsKey(id)) {
                result.add(issues.get(id));
            } else {
                notFoundIds.add(id);
            }
        }

        if (!notFoundIds.isEmpty()) {
            throw new RepositoryException("Issues with ids " + notFoundIds + " not found");
        }
        return result;
    }

    @Override
    public void deleteById(BigInteger id) throws RepositoryException {
        if (!issues.containsKey(id)) {
            throw new RepositoryException("Issue with id " + id + " not found");
        }
        issues.remove(id);
    }
}
