package com.example.personal_notes.repository;

import com.example.personal_notes.model.Note;
import com.example.personal_notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
    Note findByIdAndUser(Long id, User user);
}
