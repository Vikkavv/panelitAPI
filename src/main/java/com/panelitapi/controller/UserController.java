package com.panelitapi.controller;

import com.panelitapi.model.User;
import com.panelitapi.repository.UserRepository;
import com.panelitapi.service.AuthService;
import com.panelitapi.service.CookieService;
import com.panelitapi.service.SessionService;
import com.panelitapi.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    UserService userService;
    CookieService cookieService;
    SessionService sessionService;

    @Autowired
    public UserController(UserService userService, CookieService cookieService, SessionService sessionService) {
        this.userService = userService;
        this.cookieService = cookieService;
        this.sessionService = sessionService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody @Valid User user, HttpServletResponse response) {
        Map<String, String> errors = userService.add(user);
        if(errors.containsKey("errors") && errors.get("errors") == null) {
            Cookie cookie = cookieService.createSessionCookie();
            sessionService.add(cookie.getValue(), user);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(errors);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Map<String, String>> signIn(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {

        Map<String, String> errors = userService.signIn(email, password);
        if(errors.containsKey("errors") && errors.get("errors") == null){
            User user = userService.findUserByEmail(email);
            Cookie cookie = cookieService.createSessionCookie();
            sessionService.update(cookie.getValue(), user);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(errors);
    }

    @PostMapping("/signInWithCookie")
    public ResponseEntity<User> logInWithCookie(@CookieValue(value = "SESSIONID", defaultValue = "") String sessionId) {
        User user;
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        user = sessionService.findById(sessionId).getUser();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
