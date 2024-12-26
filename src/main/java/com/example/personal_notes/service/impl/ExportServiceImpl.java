package com.example.personal_notes.service.impl;

import com.example.personal_notes.model.Note;
import com.example.personal_notes.model.User;
import com.example.personal_notes.repository.NoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class ExportServiceImpl {
    private final NoteRepository noteRepository;
    private final ObjectMapper objectMapper;

    public ExportServiceImpl(NoteRepository noteRepository, ObjectMapper objectMapper) {
        this.noteRepository = noteRepository;
        this.objectMapper = objectMapper;
    }

    public String exportNote(Long id, User user, String format) {
        Note note = noteRepository.findByIdAndUser(id, user);
        if (note == null) {
            throw new IllegalArgumentException("Note not found");
        }
        return switch (format.toLowerCase()) {
            case "json" -> exportNoteAsJson(note);
            case "xml" -> exportNoteAsXml(note);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

    private String exportNoteAsJson(Note note) {
        try {
            return objectMapper.writeValueAsString(note);
        } catch (Exception e) {
            throw new RuntimeException("Error exporting note to JSON", e);
        }
    }

    private String exportNoteAsXml(Note note) {
        try {
            JAXBContext context = JAXBContext.newInstance(Note.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(note, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error exporting note to XML", e);
        }
    }
}
