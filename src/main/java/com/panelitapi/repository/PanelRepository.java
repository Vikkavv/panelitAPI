package com.panelitapi.repository;

import com.panelitapi.model.Panel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;


public interface PanelRepository extends JpaRepository<Panel, Long> {

    @NativeQuery("SELECT id FROM panel ORDER BY id DESC LIMIT 1")
    Long getLastPanelId();
}
