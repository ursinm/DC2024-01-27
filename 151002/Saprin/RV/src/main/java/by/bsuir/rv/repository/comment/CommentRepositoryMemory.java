package by.bsuir.rv.repository.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.repository.IRepository;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommentRepositoryMemory implements IRepository<Comment> {
    private final Map<BigInteger, Comment> comments = new ConcurrentHashMap<>();
    @Override
    public Comment save(Comment entity) {
        if (entity.getId() == null) {
            entity.setId(BigInteger.valueOf(comments.size() + 1));
        }
        comments.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Comment> findAll() {
        return List.copyOf(comments.values());
    }

    @Override
    public Comment findById(BigInteger id) throws RepositoryException {
        if (!comments.containsKey(id)) {
            throw new RepositoryException("Comment with id " + id + " not found");
        }
        return comments.get(id);
    }

    @Override
    public List<Comment> findAllById(List<BigInteger> ids) throws RepositoryException {
        List<Comment> result = new ArrayList<>();
        List<BigInteger> notFoundIds = new ArrayList<>();
        for (BigInteger id : ids) {
            if (comments.containsKey(id)) {
                result.add(comments.get(id));
            } else {
                notFoundIds.add(id);
            }
        }

        if (!notFoundIds.isEmpty()) {
            throw new RepositoryException("Comments with ids " + notFoundIds + " not found");
        }
        return result;
    }

    @Override
    public void deleteById(BigInteger id) throws RepositoryException {
        if (!comments.containsKey(id)) {
            throw new RepositoryException("Comment with id " + id + " not found");
        }
        comments.remove(id);
    }
}
