package com.panelitapi.repository;

import com.panelitapi.model.ChatIntegrant;
import com.panelitapi.model.ChatIntegrantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatIntegrantRepository extends JpaRepository<ChatIntegrant, ChatIntegrantId> {
}
