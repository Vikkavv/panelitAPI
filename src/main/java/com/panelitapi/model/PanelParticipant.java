package com.panelitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "panel_participant")
public class PanelParticipant {
    @EmbeddedId
    private PanelParticipantId id;

    @MapsId("participantId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id", nullable = false)
    @JsonIgnoreProperties({"panelParticipants"})
    private User participant;

    @MapsId("panelId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panel_id", nullable = false)
    private Panel panel;

    @NotNull
    @Column(name = "is_creator", nullable = false)
    private Boolean isCreator = false;

    @NotNull
    @Column(name = "is_Admin", nullable = false)
    private Boolean isAdmin = false;

    public PanelParticipantId getId() {
        return id;
    }

    public void setId(PanelParticipantId id) {
        this.id = id;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Boolean getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Boolean isCreator) {
        this.isCreator = isCreator;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}