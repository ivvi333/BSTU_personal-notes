package com.example.personal_notes.export;

import com.example.personal_notes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("jsonExportStrategy")
public class JsonExportStrategy implements ExportStrategy {
    private final ObjectMapper objectMapper;

    public JsonExportStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String export(Note note) {
        try {
            return objectMapper.writeValueAsString(note);
        } catch (Exception e) {
            throw new RuntimeException("Error exporting note to JSON", e);
        }
    }
}
