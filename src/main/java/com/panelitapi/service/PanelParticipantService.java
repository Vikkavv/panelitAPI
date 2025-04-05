package com.panelitapi.service;

import com.panelitapi.model.PanelParticipant;
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

    public List<PanelParticipant> getPanelParticipantsOfUser(User user) {
        List<PanelParticipant> panelsParticipants = null;
        try{
            panelsParticipants = panelParticipantRepository.findPanelParticipantsByParticipant(user);
        }catch (NoSuchElementException e){
            throw new RuntimeException("The given user doesn´t participate in any panel");
        }
        return panelsParticipants;
    }
}
