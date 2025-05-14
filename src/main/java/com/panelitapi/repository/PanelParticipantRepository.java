package com.panelitapi.repository;

import com.panelitapi.model.Panel;
import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.PanelParticipantId;
import com.panelitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PanelParticipantRepository extends JpaRepository<PanelParticipant, PanelParticipantId> {
    List<PanelParticipant> findPanelParticipantsByParticipant(User participant);

    Optional<PanelParticipant> findPanelParticipantByParticipantAndPanel(User participant, Panel panel);

    Optional<List<PanelParticipant>> findPanelParticipantsByPanel(Panel panel);

    void deletePanelParticipantsByPanel(Panel panel);
}
