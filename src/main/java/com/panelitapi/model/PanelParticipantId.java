package com.panelitapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class PanelParticipantId implements java.io.Serializable {
    private static final long serialVersionUID = -4759506288353038521L;
    @NotNull
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @NotNull
    @Column(name = "panel_id", nullable = false)
    private Long panelId;

    public PanelParticipantId(Long participantId, Long panelId) {
        this.participantId = participantId;
        this.panelId = panelId;
    }

    public PanelParticipantId() {}

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getPanelId() {
        return panelId;
    }

    public void setPanelId(Long panelId) {
        this.panelId = panelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PanelParticipantId entity = (PanelParticipantId) o;
        return Objects.equals(this.participantId, entity.participantId) &&
                Objects.equals(this.panelId, entity.panelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, panelId);
    }

}