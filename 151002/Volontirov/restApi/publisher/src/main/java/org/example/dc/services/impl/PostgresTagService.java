package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Tag;
import org.example.dc.model.TagDto;
import org.example.dc.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresTagService implements TagService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<TagDto> getTags() {
        return jdbcTemplate.query("select * from tbl_tag", new BeanPropertyRowMapper<>(Tag.class)).stream()
                .map(tag -> converter.convert(tag)).collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_tag where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Tag.class)).get(0));
    }

    @Override
    public boolean delete(int id) throws Exception {
        if (jdbcTemplate.query("select * from tbl_tag", new BeanPropertyRowMapper<>(Tag.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_tag where id=?", new Object[]{id});
        return true;
    }

    @Override
    public TagDto updateTag(TagDto tagDto) {
        jdbcTemplate.update("update tbl_tag set name=? where id=?",
                new Object[]{tagDto.getName(), tagDto.getId()});
        return tagDto;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_tag").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(tagDto));
        tagDto.setId(number.intValue());
        return tagDto;
    }
}
