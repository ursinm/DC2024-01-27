package by.bsuir.springapi.service;

import by.bsuir.springapi.model.entity.Post;
import by.bsuir.springapi.model.entity.Sticker;
import by.bsuir.springapi.model.request.StickerRequestTo;
import by.bsuir.springapi.model.response.StickerResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.PostMapper;
import by.bsuir.springapi.service.mapper.StickerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresStickerService implements RestService<StickerRequestTo, StickerResponseTo>{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StickerMapper stickerMapper;

    @Override
    public List<StickerResponseTo> findAll() {
        return jdbcTemplate.query("select * from sticker", new BeanPropertyRowMapper<>(Sticker.class)).stream()
                .map(stickerMapper::getResponseTo).collect(Collectors.toList());
    }

    @Override
    public StickerResponseTo findById(Long id) {
        return jdbcTemplate.query("select * from sticker where id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Sticker.class)).stream()
                .map(stickerMapper::getResponseTo)
                .findFirst()
                .orElseThrow(() -> stickerNotFoundException(id));
    }

    @Override
    public StickerResponseTo create(StickerRequestTo creatorTo) {
        Sticker sticker = stickerMapper.getTag(creatorTo);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = null;
        try {
            number = insert.withTableName("sticker").usingGeneratedKeyColumns("id")
                    .executeAndReturnKey(new BeanPropertySqlParameterSource(sticker));
        } catch (Exception e) {
            throw stickerStateException();
        }
        sticker.setId(number.longValue());
        return stickerMapper.getResponseTo(sticker);
    }

    @Override
    public StickerResponseTo update(StickerRequestTo creatorTo) {
        Sticker sticker = stickerMapper.getTag(creatorTo);
        try {
            jdbcTemplate.update("update sticker set  name=? where id=?",
                    new Object[]{sticker.getName(),  sticker.getId()});
        } catch (Exception e) {
            throw stickerStateException();
        }
        return stickerMapper.getResponseTo(sticker);
    }

    @Override
    public boolean removeById(Long id) {
        if (jdbcTemplate.query("select * from sticker where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Sticker.class)).isEmpty()) {
            throw stickerNotFoundException(id);
        }
        jdbcTemplate.update("delete from sticker where id=?", new Object[]{id});
        return true;
    }
    private static ResourceNotFoundException stickerNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find news with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 53);
    }

    private static ResourceStateException stickerStateException() {
        return new ResourceStateException("Failed to create/update news with specified credentials", HttpStatus.CONFLICT.value() * 100 + 54);
    }
}
