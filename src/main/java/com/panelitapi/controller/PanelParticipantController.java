package com.panelitapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panelitapi.model.Panel;
import com.panelitapi.model.PanelParticipant;
import com.panelitapi.model.User;
import com.panelitapi.service.PanelParticipantService;
import com.panelitapi.service.PanelService;
import com.panelitapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/PanelParticipant")
public class PanelParticipantController {

    private final PanelService panelService;
    private final UserService userService;
    PanelParticipantService panelParticipantService;
    ObjectMapper mapper;

    @Autowired
    public PanelParticipantController(PanelParticipantService panelParticipantService, ObjectMapper mapper, PanelService panelService, UserService userService) {
        this.panelParticipantService = panelParticipantService;
        this.mapper = mapper;
        this.panelService = panelService;
        this.userService = userService;
    }

    @PostMapping("/findByPanel")
    public ResponseEntity<List<PanelParticipant>> findByPanel(@RequestBody Panel panel) {
        return ResponseEntity.ok(panelParticipantService.findPanelParticipantsByPanel(panel));
    }

    @PostMapping("/addFromArrayToPanel/{panelId}")
    public ResponseEntity<Boolean> addFromArray(@RequestBody Long[] usersId, @PathVariable Long panelId) throws JsonProcessingException {
        Panel panel = panelService.findById(panelId);
        List<User> users = new ArrayList<>();
        for(Long userId : usersId) {
            users.add(userService.findById(userId));
        }
        return ResponseEntity.ok(panelParticipantService.addFromArray(panel, users));
    }

    @DeleteMapping("/deleteFromArrayInPanel/{panelId}")
    public ResponseEntity<Boolean> deleteFromArray(@RequestBody Long[] usersId, @PathVariable Long panelId) {
        Panel panel = panelService.findById(panelId);
        List<User> users = new ArrayList<>();
        for(Long userId : usersId) {
            users.add(userService.findById(userId));
        }
        return ResponseEntity.ok(panelParticipantService.deleteFromArray(panel, users));
    }

    @PostMapping(value = "/findByUserAndPanel", consumes = "multipart/form-data")
    public ResponseEntity<PanelParticipant> findPanelParticipantByUserAndPanel(@RequestPart("user") String userJson, @RequestPart("panel") String panelJson, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId) throws JsonProcessingException {
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = mapper.treeToValue(mapper.readTree(userJson), User.class);
        Panel panel = mapper.treeToValue(mapper.readTree(panelJson), Panel.class);

        return ResponseEntity.ok(panelParticipantService.findByUserAndPanel(user, panel));
    }

}
