package com.example.personal_notes.service.impl;

import com.example.personal_notes.model.Note;
import com.example.personal_notes.model.User;
import com.example.personal_notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl {
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findByUser(User user) {
        return noteRepository.findByUser(user);
    }

    public Note saveByUser(Note note, User user) {
        note.setUser(user);
        return noteRepository.save(note);
    }

    public Note findByIdAndUser(Long id, User user) {
        return noteRepository.findByIdAndUser(id, user);
    }

    public boolean deleteByIdAndUser(Long id, User user) {
        Note note = findByIdAndUser(id, user);
        if (note != null) {
            noteRepository.delete(note);
            return true;
        }
        return false;
    }
}
