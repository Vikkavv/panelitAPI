package com.panelitapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panelitapi.model.User;
import com.panelitapi.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Follower")
public class FollowerController {

    FollowerService followerService;
    ObjectMapper mapper;

    @Autowired
    public FollowerController(FollowerService followerService, ObjectMapper mapper) {
        this.followerService = followerService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/follow", consumes = "multipart/form-data")
    public ResponseEntity<Boolean> addFollow(@RequestPart("follower") String strFollower, @RequestPart("followed") String strFollowed, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId) throws JsonProcessingException {
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User follower = mapper.treeToValue(mapper.readTree(strFollower), User.class);
        User followed = mapper.treeToValue(mapper.readTree(strFollowed), User.class);

        return ResponseEntity.ok(followerService.addFollow(follower, followed));
    }

    @PostMapping(value = "/unfollow", consumes = "multipart/form-data")
    public ResponseEntity<Boolean> removeFollow(@RequestPart("follower") String strFollower, @RequestPart("followed") String strFollowed, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId) throws JsonProcessingException {
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User follower = mapper.treeToValue(mapper.readTree(strFollower), User.class);
        User followed = mapper.treeToValue(mapper.readTree(strFollowed), User.class);

        return ResponseEntity.ok(followerService.removeFollow(follower, followed));
    }

    @GetMapping("/FollowersOfUser/{userId}")
    public ResponseEntity<List<User>> FollowersOfUser(@PathVariable long userId) {
        return ResponseEntity.ok(followerService.findFollowersOfUser(userId));
    }

    @GetMapping("/FollowedOfUser/{userId}")
    public ResponseEntity<List<User>> FollowedOfUser(@PathVariable long userId) {
        return ResponseEntity.ok(followerService.findFollowedOfUser(userId));
    }
}
