package com.panelitapi.service;

import com.panelitapi.model.Panel;
import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.User;
import com.panelitapi.repository.PanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PanelService {

    private PanelRepository panelRepository;
    private PanelParticipantService panelParticipantService;

    @Autowired
    public PanelService(PanelRepository panelRepository, PanelParticipantService panelParticipantService) {
        this.panelRepository = panelRepository;
        this.panelParticipantService = panelParticipantService;
    }

    public List<Panel> getPanelsOfUser(User user) {
        List<PanelParticipant> panelParticipants = panelParticipantService.getPanelParticipantsOfUser(user);
        List<Panel> panels = new ArrayList<>();
        for (PanelParticipant panelParticipant : panelParticipants) {
            panels.add(panelParticipant.getPanel());
        }
        return panels;
    }
}
