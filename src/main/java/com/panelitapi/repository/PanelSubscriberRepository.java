package com.panelitapi.repository;

import com.panelitapi.model.PanelSubscriber;
import com.panelitapi.model.PanelSubscriberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelSubscriberRepository extends JpaRepository<PanelSubscriber, PanelSubscriberId> {
}
