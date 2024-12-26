package com.example.personal_notes.export;

import com.example.personal_notes.model.Note;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component("xmlExportStrategy")
public class XmlExportStrategy implements ExportStrategy {
    @Override
    public String export(Note note) {
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
