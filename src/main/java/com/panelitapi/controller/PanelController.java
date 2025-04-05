package com.panelitapi.controller;

import com.panelitapi.model.Panel;
import com.panelitapi.model.User;
import com.panelitapi.service.PanelService;
import com.panelitapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Panel")
public class PanelController {
    private PanelService panelService;
    private UserService userService;

    @Autowired
    public PanelController(PanelService panelService, UserService userService) {
        this.panelService = panelService;
        this.userService = userService;
    }

    @GetMapping("/ofUser/{nickname}")
    public ResponseEntity<List<Panel>> getPanelsOfUser(@PathVariable String nickname) {
        User user = userService.getGeneralUserInfoByNickname(nickname);
        return ResponseEntity.ok(panelService.getPanelsOfUser(user));
    }
}
