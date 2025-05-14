package com.panelitapi.service;

import com.panelitapi.model.Panel;
import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.PanelParticipantId;
import com.panelitapi.model.User;
import com.panelitapi.repository.PanelParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PanelParticipantService {

    private PanelParticipantRepository panelParticipantRepository;

    @Autowired
    public PanelParticipantService(PanelParticipantRepository panelParticipantRepository) {
        this.panelParticipantRepository = panelParticipantRepository;
    }

    public List<PanelParticipant>  getPanelParticipantsOfUser(User user) {
        List<PanelParticipant> panelsParticipants = null;
        try{
            panelsParticipants = panelParticipantRepository.findPanelParticipantsByParticipant(user);
        }catch (NoSuchElementException e){
            throw new RuntimeException("The given user doesn´t participate in any panel");
        }
        return panelsParticipants;
    }

    public PanelParticipant findByUserAndPanel(User user, Panel panel) {
        return panelParticipantRepository.findPanelParticipantByParticipantAndPanel(user, panel).orElse(null);
    }

    public List<PanelParticipant> findPanelParticipantsByPanel(Panel panel) {
        return panelParticipantRepository.findPanelParticipantsByPanel(panel).orElseThrow(() -> new RuntimeException("The given panel doesn't exist"));
    }

    public void deletePanelParticipantsByPanel(Panel panel) {
        panelParticipantRepository.deletePanelParticipantsByPanel(panel);
    }

    public boolean addFromArray(Panel panel,List<User> users){
        for(User user : users){
            addPanelParticipant(user, panel, false, true);
        }
        return true;
    }

    public boolean deleteFromArray(Panel panel,List<User> users){
        for(User user : users){
            deletePanelParticipant(findByUserAndPanel(user,panel));
        }
        return true;
    }

    public boolean deletePanelParticipant(PanelParticipant panelParticipant){
        panelParticipantRepository.delete(panelParticipant);
        return true;
    }

    public PanelParticipant addPanelParticipant(User participant, Panel panel, boolean isCreator, boolean isAdmin) {
        PanelParticipantId panelParticipantId = new PanelParticipantId(participant.getId(), panel.getId());
        PanelParticipant panelParticipant = new PanelParticipant(panelParticipantId, participant, panel, isCreator, isAdmin);

        return panelParticipantRepository.save(panelParticipant);
    }
}
