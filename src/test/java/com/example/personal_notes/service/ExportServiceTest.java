package com.example.personal_notes.service;

import com.example.personal_notes.export.ExportStrategy;
import com.example.personal_notes.model.Note;
import com.example.personal_notes.model.User;
import com.example.personal_notes.repository.NoteRepository;
import com.example.personal_notes.service.impl.ExportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExportServiceTest {
    private ExportServiceImpl exportService;
    private NoteRepository noteRepository;
    private ExportStrategy jsonExportStrategy;
    private ExportStrategy xmlExportStrategy;

    @BeforeEach
    public void setUp() {
        noteRepository = Mockito.mock(NoteRepository.class);
        jsonExportStrategy = Mockito.mock(ExportStrategy.class);
        xmlExportStrategy = Mockito.mock(ExportStrategy.class);

        exportService = new ExportServiceImpl(noteRepository, Map.of(
                "jsonExportStrategy", jsonExportStrategy,
                "xmlExportStrategy", xmlExportStrategy
        ));
    }

    @Test
    public void testExportUserNotesAsJson() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("Test user");
        user.setPassword("qwerty");

        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Note 1");
        note1.setContent("Content 1");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Note 2");
        note2.setContent("Content 2");

        List<Note> notes = Arrays.asList(note1, note2);

        Mockito.when(noteRepository.findByUser(user)).thenReturn(notes);
        Mockito.when(jsonExportStrategy.export(note1)).thenReturn(
                "{\"id\":1,\"title\":\"Note 1\",\"content\":\"Content 1\"}"
        );
        Mockito.when(jsonExportStrategy.export(note2)).thenReturn(
                "{\"id\":2,\"title\":\"Note 2\",\"content\":\"Content 2\"}"
        );

        // Act
        String result = exportService.exportUserNotes(user,"json");

        // Assert
        assertEquals("[{\"id\":1,\"title\":\"Note 1\",\"content\":\"Content 1\"},{\"id\":2,\"title\":\"Note 2\",\"content\":\"Content 2\"}]", result);
    }
}
