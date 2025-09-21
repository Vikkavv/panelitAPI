package com.panelitapi.service;

import com.panelitapi.model.Session;
import com.panelitapi.model.User;
import com.panelitapi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private Session session;

    @Autowired
    public SessionService(SessionRepository sessionRepository, Session session) {
        this.sessionRepository = sessionRepository;
        this.session = session;
    }

    public void add(String uuid, User user) {
        session.setUuid(uuid);
        session.setUser(user);
        sessionRepository.save(session);
    }

    public void update(String uuid, User user) {
        session = sessionRepository.findSessionByUser(user).orElseThrow();
        sessionRepository.delete(session);

        // We create a new session to prevent hibernate persistant issues
        Session sessionToSave = new Session();
        sessionToSave.setUser(user);
        sessionToSave.setUuid(uuid);
        sessionRepository.save(sessionToSave);
    }

    public Session findById(String uuid) {
        return sessionRepository.findById(uuid).orElseThrow();
    }
}
