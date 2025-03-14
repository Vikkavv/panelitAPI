package com.panelitapi.repository;

import com.panelitapi.model.Panel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelRepository extends JpaRepository<Panel, Long> {
}
