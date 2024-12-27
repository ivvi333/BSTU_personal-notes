package com.example.personal_notes.controller.impl;

import com.example.personal_notes.model.Note;
import com.example.personal_notes.service.impl.ExportServiceImpl;
import com.example.personal_notes.util.CustomUserDetails;
import com.example.personal_notes.service.impl.NoteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/notes")
public class NoteControllerImpl {
    private final NoteServiceImpl noteService;
    private final ExportServiceImpl exportService;

    public NoteControllerImpl(NoteServiceImpl noteService, ExportServiceImpl exportService) {
        this.noteService = noteService;
        this.exportService = exportService;
    }

    @GetMapping
    public List<Note> getUserNotes(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return noteService.findByUser(userDetails.user());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Note note) {
        return noteService.saveByUser(note, userDetails.user());
    }

    @PostMapping("/{id}")
    public Note changeNoteByIdAndUser(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id, @RequestBody Note newNote) {
        Note note = noteService.changeNoteByIdAndUser(id, userDetails.user(), newNote);
        if (note == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find note");
        return note;
    }

    @GetMapping("/{id}")
    public Note getNoteByIdAndUser(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
        Note note = noteService.findByIdAndUser(id, userDetails.user());
        if (note == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find note");
        return note;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
        boolean isDeleted = noteService.deleteByIdAndUser(id, userDetails.user());
        if (!isDeleted) throw new ResponseStatusException(NOT_FOUND, "Unable to find note");
    }

    @GetMapping("/{id}/export")
    public String exportNote(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id, @RequestParam String format) {
        return exportService.exportNote(id, userDetails.user(), format);
    }
}
