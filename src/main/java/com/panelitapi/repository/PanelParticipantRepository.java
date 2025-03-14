package com.panelitapi.repository;

import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.PanelParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelParticipantRepository extends JpaRepository<PanelParticipant, PanelParticipantId> {
}
