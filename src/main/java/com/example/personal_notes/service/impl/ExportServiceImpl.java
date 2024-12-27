package com.example.personal_notes.service.impl;

import com.example.personal_notes.export.ExportStrategy;
import com.example.personal_notes.model.Note;
import com.example.personal_notes.model.User;
import com.example.personal_notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private CharSequence getFormatDelimiter(String format) {
        return switch (format.toLowerCase()) {
            case "json" -> ",";
            case "xml" -> "";
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

    public String exportUserNotes(User user, String format) {
        List<Note> notes = noteRepository.findByUser(user);

        ExportStrategy strategy = strategies.get(format.toLowerCase() + "ExportStrategy");
        if (strategy == null) {
            throw new IllegalArgumentException("No such export strategy: " + format);
        }

        String notesExported = notes.stream()
                .map(strategy::export)
                .collect(Collectors.joining(getFormatDelimiter(format.toLowerCase())));

        return switch (format.toLowerCase()) {
            case "json" -> "[" + notesExported + "]";
            case "xml" -> "<notes>" + notesExported + "</notes>";
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
