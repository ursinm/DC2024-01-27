package by.bsuir.rv.repository.editor;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.repository.IRepository;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EditorRepositoryMemory implements IRepository<Editor> {
    private final Map<BigInteger, Editor> editors = new ConcurrentHashMap<>();

    @Override
    public Editor save(Editor entity) {
        if (entity.getId() == null) {
            entity.setId(BigInteger.valueOf(editors.size() + 1));
        }
        editors.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Editor> findAll() {
        return List.copyOf(editors.values());
    }

    @Override
    public Editor findById(BigInteger id) throws RepositoryException {
        if (!editors.containsKey(id)) {
            throw new RepositoryException("Editor with id " + id + " not found");
        }
        return editors.get(id);
    }

    @Override
    public List<Editor> findAllById(List<BigInteger> ids) throws RepositoryException {
        List<Editor> result = new ArrayList<>();
        List<BigInteger> notFoundIds = new ArrayList<>();
        for (BigInteger id : ids) {
            if (editors.containsKey(id)) {
                result.add(editors.get(id));
            } else {
                notFoundIds.add(id);
            }
        }

        if (!notFoundIds.isEmpty()) {
            throw new RepositoryException("Editors with ids " + notFoundIds + " not found");
        }
        return result;
    }

    @Override
    public void deleteById(BigInteger id) throws RepositoryException {
        if (!editors.containsKey(id)) {
            throw new RepositoryException("Editor with id " + id + " not found");
        }
        editors.remove(id);
    }
}
