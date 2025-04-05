package com.panelitapi.repository;

import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.PanelParticipantId;
import com.panelitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PanelParticipantRepository extends JpaRepository<PanelParticipant, PanelParticipantId> {
    List<PanelParticipant> findPanelParticipantsByParticipant(User participant);
}
