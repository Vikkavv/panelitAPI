package com.panelitapi.controller;

import com.panelitapi.model.Panel;
import com.panelitapi.model.User;
import com.panelitapi.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/Panel")
public class PanelController {
    private PanelService panelService;
    private UserService userService;
    private SessionService sessionService;

    @Autowired
    public PanelController(PanelService panelService, UserService userService, SessionService sessionService) {
        this.panelService = panelService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/ofUser/{nickname}")
    public ResponseEntity<List<Panel>> getPanelsOfUser(@PathVariable String nickname) {
        User user = userService.getGeneralUserInfoByNickname(nickname);
        return ResponseEntity.ok(panelService.getPanelsOfUser(user));
    }

    @GetMapping("/find100")
    public ResponseEntity<List<Panel>> find100() {
        return ResponseEntity.ok(panelService.find100());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Panel>> findAll() {
        return ResponseEntity.ok(panelService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Panel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(panelService.findById(id));
    }

    @PutMapping("/Update")
    public ResponseEntity<Boolean> updatePanel(@RequestBody Panel panel) {
        return ResponseEntity.ok(panelService.updatePanel(panel));
    }

    @PostMapping(value = "/Create", consumes = "multipart/form-data")
    public ResponseEntity<Long> createPanel(@RequestPart("name") String name, @RequestPart("additionalInfoJson") String jsonInfo, @RequestPart(name = "coverPhoto", required = false) MultipartFile coverPhoto, @RequestPart(name = "backgroundPhoto", required = false) MultipartFile backgroundPhoto, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId, HttpServletRequest request) throws IOException {
        User user = null;
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else user = sessionService.findById(sessionId).getUser();

        return ResponseEntity.ok(panelService.createPanel(name, user, coverPhoto, backgroundPhoto, jsonInfo, request));
    }

    @DeleteMapping("/Delete")
    @Transactional
    public ResponseEntity<Boolean> delete(@RequestBody Panel panel, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId) {
         User user = null;
         if(sessionId != null || !(sessionId.isEmpty()))
             user = sessionService.findById(sessionId).getUser();
         if((sessionId == null || sessionId.isEmpty()) || (!panel.getCreatorId().equals(user.getId()))) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
         }
         return ResponseEntity.ok(panelService.deletePanel(panel.getId()));
    }
}
