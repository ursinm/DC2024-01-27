package com.example.lab2.Repository;

import com.example.lab2.Model.Editor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor,Integer>{

    Page<Editor> findAll (Pageable pageable);
    boolean existsByLogin(String login);
}
