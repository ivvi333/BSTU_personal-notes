package com.example.personal_notes.service.impl;

import com.example.personal_notes.export.ExportStrategy;
import com.example.personal_notes.model.Note;
import com.example.personal_notes.model.User;
import com.example.personal_notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExportServiceImpl {
    private final NoteRepository noteRepository;
    private final Map<String, ExportStrategy> strategies;

    public ExportServiceImpl(NoteRepository noteRepository, Map<String, ExportStrategy> strategies) {
        this.noteRepository = noteRepository;
        this.strategies = strategies;
    }

    public String exportNote(Long id, User user, String format) {
        Note note = noteRepository.findByIdAndUser(id, user);
        if (note == null) {
            throw new IllegalArgumentException("Note not found");
        }
        ExportStrategy strategy = strategies.get(format.toLowerCase() + "ExportStrategy");
        if (strategy == null) {
            throw new IllegalArgumentException("No such export strategy: " + format);
        }
        return strategy.export(note);
    }
}
