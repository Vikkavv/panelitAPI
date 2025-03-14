package com.panelitapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class PanelSubscriberId implements java.io.Serializable {
    private static final long serialVersionUID = 3504740737634522205L;
    @NotNull
    @Column(name = "subscriber_id", nullable = false)
    private Long subscriberId;

    @NotNull
    @Column(name = "panel_id", nullable = false)
    private Long panelId;

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
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
        PanelSubscriberId entity = (PanelSubscriberId) o;
        return Objects.equals(this.panelId, entity.panelId) &&
                Objects.equals(this.subscriberId, entity.subscriberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panelId, subscriberId);
    }

}