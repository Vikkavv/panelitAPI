package com.panelitapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Embeddable
public class ChatIntegrantId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -7950501638789537019L;
    @NotNull
    @Column(name = "integrant_id", nullable = false)
    private Long integrantId;

    @NotNull
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    public Long getIntegrantId() {
        return integrantId;
    }

    public void setIntegrantId(Long integrantId) {
        this.integrantId = integrantId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatIntegrantId entity = (ChatIntegrantId) o;
        return Objects.equals(this.chatId, entity.chatId) &&
                Objects.equals(this.integrantId, entity.integrantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, integrantId);
    }

}