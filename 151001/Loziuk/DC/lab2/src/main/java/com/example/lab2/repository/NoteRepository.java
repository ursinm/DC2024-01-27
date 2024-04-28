package com.example.lab2.repository;

import com.example.lab2.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<Note,Integer> {
    Page<Note> findAll(Pageable pageable);
}
