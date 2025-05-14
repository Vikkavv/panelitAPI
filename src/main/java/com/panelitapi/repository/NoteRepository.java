package com.panelitapi.repository;

import com.panelitapi.model.Note;
import com.panelitapi.model.Panel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    void deleteAllByPanel(Panel panel);
}
