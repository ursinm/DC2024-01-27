package by.bsuir.rv.repository.sticker;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.repository.IRepository;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StickerRepositoryMemory implements IRepository<Sticker> {

    private final Map<BigInteger, Sticker> stickers = new ConcurrentHashMap<>();

    @Override
    public Sticker save(Sticker entity) {
        if (entity.getId() == null) {
            entity.setId(BigInteger.valueOf(stickers.size() + 1));
        }
        stickers.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Sticker> findAll() {
        return List.copyOf(stickers.values());
    }

    @Override
    public Sticker findById(BigInteger id) throws RepositoryException {
        if (!stickers.containsKey(id)) {
            throw new RepositoryException("Sticker with id " + id + " not found");
        }
        return stickers.get(id);
    }

    @Override
    public List<Sticker> findAllById(List<BigInteger> ids) throws RepositoryException {
        List<Sticker> result = new ArrayList<>();
        List<BigInteger> notFoundIds = new ArrayList<>();
        for (BigInteger id : ids) {
            if (stickers.containsKey(id)) {
                result.add(stickers.get(id));
            } else {
                notFoundIds.add(id);
            }
        }

        if (!notFoundIds.isEmpty()) {
            throw new RepositoryException("Stickers with ids " + notFoundIds + " not found");
        }
        return result;
    }

    @Override
    public void deleteById(BigInteger id) throws RepositoryException {
        if (!stickers.containsKey(id)) {
            throw new RepositoryException("Sticker with id " + id + " not found");
        }
        stickers.remove(id);
    }
}
