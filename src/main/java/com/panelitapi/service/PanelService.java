package com.panelitapi.service;

import com.panelitapi.model.Panel;
import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.User;
import com.panelitapi.repository.NoteRepository;
import com.panelitapi.repository.PanelRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PanelService {

    private PanelRepository panelRepository;
    private PanelParticipantService panelParticipantService;
    private NoteRepository noteRepository;
    private FileStorageService imageStorageService;

    @Autowired
    public PanelService(PanelRepository panelRepository, PanelParticipantService panelParticipantService, NoteRepository noteRepository ,FileStorageService fileStorageService) {
        this.panelRepository = panelRepository;
        this.panelParticipantService = panelParticipantService;
        this.noteRepository = noteRepository;
        this.imageStorageService = fileStorageService;
    }

    public Panel findById(Long id){
        return panelRepository.findById(id).orElseThrow();
    }

    public List<Panel> getPanelsOfUser(User user) {
        List<PanelParticipant> panelParticipants = panelParticipantService.getPanelParticipantsOfUser(user);
        List<Panel> panels = new ArrayList<>();
        for (PanelParticipant panelParticipant : panelParticipants) {
            panels.add(panelParticipant.getPanel());
        }
        return panels;
    }

    public Long createPanel(String name, User creator, MultipartFile coverPhoto, MultipartFile backgroundPhoto, String json, HttpServletRequest request) throws IOException {
        Panel panel = new Panel();
        panel.setName(name);
        panel.setCreatorId(creator.getId());
        panel.setCreationDate(LocalDate.now());
        panel.setLastEditedDate(LocalDate.now());
        panel.setIsBlocked(false);
        panel.setAdditionalInfo(json);

        if(coverPhoto != null) {
            String imageName = imageStorageService.savePanelImage(coverPhoto);
            panel.setCoverPhoto(imageStorageService.getPanelImgUrl(imageName,request));
        }
        if(backgroundPhoto != null) {
            String imageName = imageStorageService.savePanelImage(backgroundPhoto);
            panel.setBackgroundPhoto(imageStorageService.getPanelImgUrl(imageName,request));
        }
        panel = panelRepository.save(panel);
        panelParticipantService.addPanelParticipant(creator, panel,true,true);
        return panel.getId();
    }

    public Boolean updatePanel(Panel panel){
        panel.setLastEditedDate(LocalDate.now());
        panelRepository.save(panel);
        return true;
    }

    public Boolean deletePanel(Long id){
        panelParticipantService.deletePanelParticipantsByPanel(findById(id));
        noteRepository.deleteAllByPanel(findById(id));
        panelRepository.deleteById(id);
        return true;
    }
}
