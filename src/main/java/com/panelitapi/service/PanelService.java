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
import java.util.*;

@Service
public class PanelService {

    private PanelRepository panelRepository;
    private PanelParticipantService panelParticipantService;
    private NoteRepository noteRepository;
    private FileStorageService imageStorageService;
    private CloudinaryService cloudinaryService;

    @Autowired
    public PanelService(PanelRepository panelRepository, PanelParticipantService panelParticipantService, NoteRepository noteRepository ,FileStorageService fileStorageService, CloudinaryService cloudinaryService) {
        this.panelRepository = panelRepository;
        this.panelParticipantService = panelParticipantService;
        this.noteRepository = noteRepository;
        this.imageStorageService = fileStorageService;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Panel> find100(){
        Set<Panel> panels = new HashSet<>();
        Random rand = new Random();
        long nPanels = panelRepository.count();
        long lastId = panelRepository.getLastPanelId();
        for (int i = 0; i < 100; i++) {
            if(panels.size() == nPanels) break;
            long randomLong = rand.nextLong(lastId) + 1;
            Panel panel = panelRepository.findById(randomLong).orElse(null);
            if(panel != null) panels.add(panel);
        }
        List<Panel> panelsList = new ArrayList<>(panels);
        Collections.shuffle(panelsList);
        return panelsList;
    }

    public List<Panel> findAll(){
        return panelRepository.findAll();
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
            String imageName = cloudinaryService.uploadFile(coverPhoto, "panels");
            panel.setCoverPhoto(imageName);
            // String imageName = imageStorageService.savePanelImage(coverPhoto);
            // panel.setCoverPhoto(imageStorageService.getPanelImgUrl(imageName,request));
        }
        if(backgroundPhoto != null) {
            String imageName = cloudinaryService.uploadFile(backgroundPhoto, "panels");
            panel.setCoverPhoto(imageName);
            // String imageName = imageStorageService.savePanelImage(backgroundPhoto);
            // panel.setBackgroundPhoto(imageStorageService.getPanelImgUrl(imageName,request));
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
