package com.panelitapi.service;

import com.panelitapi.model.Note;
import com.panelitapi.model.Panel;
import com.panelitapi.repository.NoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class NoteService {

    NoteRepository noteRepository;
    UserService userService;
    PanelService panelService;
    FileStorageService fileStorageService;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserService userService, PanelService panelService, FileStorageService fileStorageService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
        this.panelService = panelService;
        this.fileStorageService = fileStorageService;
    }

    public Note findById(Long id){
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public Boolean delete(Note note){
        noteRepository.delete(note);
        return true;
    }

    public void deleteByPanel(Panel panel){
        noteRepository.deleteAllByPanel(panel);
    }

    public Long addPdfNote(Note note, MultipartFile pdf, HttpServletRequest request) throws IOException {
        note.setOwner(userService.findById(note.getOwner().getId()));
        note.setPanel(panelService.findById(note.getPanel().getId()));
        note.setLastEditedDate(LocalDate.now());
        String fileName = fileStorageService.savePdfFile(pdf);
        note.setResourceUrl(fileStorageService.getDocumentUrl(fileName, request));
        return noteRepository.save(note).getId();
    }

    public Long add(Note note){
        note.setOwner(userService.findById(note.getOwner().getId()));
        note.setPanel(panelService.findById(note.getPanel().getId()));
        note.setLastEditedDate(LocalDate.now());
        return noteRepository.save(note).getId();
    }

    public Boolean update(Note note){
        note.setOwner(userService.findById(note.getOwner().getId()));
        note.setPanel(panelService.findById(note.getPanel().getId()));
        note.setLastEditedDate(LocalDate.now());
        noteRepository.save(note);
        return true;
    }
}
