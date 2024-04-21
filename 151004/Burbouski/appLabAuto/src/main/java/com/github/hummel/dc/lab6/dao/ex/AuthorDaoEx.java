package com.github.hummel.dc.lab6.dao.ex;

import com.github.hummel.dc.lab6.bean.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("InterfaceNeverImplemented")
@Repository
public interface AuthorDaoEx extends JpaRepository<Author, Integer> {
}