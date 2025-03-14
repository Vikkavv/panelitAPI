package com.panelitapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "chat_integrant")
public class ChatIntegrant {
    @EmbeddedId
    private ChatIntegrantId id;

    @MapsId("integrantId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "integrant_id", nullable = false)
    private User integrant;

    @MapsId("chatId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @NotNull
    @Column(name = "is_creator", nullable = false)
    private Boolean isCreator = false;

    @NotNull
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;

    public ChatIntegrantId getId() {
        return id;
    }

    public void setId(ChatIntegrantId id) {
        this.id = id;
    }

    public User getIntegrant() {
        return integrant;
    }

    public void setIntegrant(User integrant) {
        this.integrant = integrant;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
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