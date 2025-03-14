package com.panelitapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "panel_subscriber")
public class PanelSubscriber {
    @EmbeddedId
    private PanelSubscriberId id;

    @MapsId("subscriberId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscriber_id", nullable = false)
    private User subscriber;

    @MapsId("panelId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panel_id", nullable = false)
    private Panel panel;

    public PanelSubscriberId getId() {
        return id;
    }

    public void setId(PanelSubscriberId id) {
        this.id = id;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

}