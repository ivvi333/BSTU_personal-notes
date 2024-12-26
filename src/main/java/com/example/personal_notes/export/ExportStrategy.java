package com.example.personal_notes.export;

import com.example.personal_notes.model.Note;

public interface ExportStrategy {
    String export(Note note);
}
