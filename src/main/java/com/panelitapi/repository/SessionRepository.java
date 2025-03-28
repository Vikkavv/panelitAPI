package com.panelitapi.repository;

import com.panelitapi.model.Session;
import com.panelitapi.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {
    Optional<Session> findSessionByUser(@NotNull User user);
}
