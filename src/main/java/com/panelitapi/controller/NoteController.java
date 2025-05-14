package com.panelitapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panelitapi.model.Note;
import com.panelitapi.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/Note")
public class NoteController {

    NoteService noteService;
    ObjectMapper mapper;

    @Autowired
    public NoteController(NoteService noteService, ObjectMapper mapper) {
        this.noteService = noteService;
        this.mapper = mapper;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Note note = noteService.findById(id);
        return ResponseEntity.ok(noteService.delete(note));
    }

    @PostMapping("/Create")
    public ResponseEntity<Long> addNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.add(note));
    }

    @PostMapping("/CreateWithPdf")
    public ResponseEntity<Long> addNoteWithPdf(@RequestPart("note") String strNote, @RequestPart("pdf") MultipartFile pdf, HttpServletRequest request) throws IOException {
        Long newNoteId;
        Note note = mapper.treeToValue(mapper.readTree(strNote), Note.class);
        if(pdf != null) {
            newNoteId = noteService.addPdfNote(note, pdf, request);
        }
        else throw new RuntimeException("pdf is null");

        return ResponseEntity.ok(newNoteId);
    }

    @PutMapping("/Update")
    public ResponseEntity<Boolean> updateNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.update(note));
    }
}
