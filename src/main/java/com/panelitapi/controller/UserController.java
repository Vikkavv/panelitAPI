package com.panelitapi.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panelitapi.model.User;
import com.panelitapi.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    UserService userService;
    CookieService cookieService;
    SessionService sessionService;
    ImageStorageService imageStorageService;
    AuthService authService;
    ObjectMapper mapper;

    @Autowired
    public UserController(UserService userService, CookieService cookieService, SessionService sessionService, ImageStorageService imageStorageService, AuthService authService, ObjectMapper mapper) {
        this.userService = userService;
        this.cookieService = cookieService;
        this.sessionService = sessionService;
        this.imageStorageService = imageStorageService;
        this.authService = authService;
        this.mapper = mapper;
    }

    @GetMapping("/findByNickname/{nickname}")
    public ResponseEntity<User> getGeneralUserInfoByNickname(@PathVariable String nickname) {
        User user = userService.getGeneralUserInfoByNickname(nickname);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> getGeneralUserInfoById(@PathVariable Long id) {
        User user = userService.getGeneralUserInfoById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/existsUserWithPhoneNumber/{phoneNumber}")
    public ResponseEntity<Boolean> existsUserWithPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(userService.existsUserWithPhoneNumber(phoneNumber));
    }

    @GetMapping("/existsUserWithNickname/{nickname}")
    public ResponseEntity<Boolean> existsUserWithNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.existsUserWithNickname(nickname));
    }

    @GetMapping("/existsUserWithEmail/{email}")
    public ResponseEntity<Boolean> existsUserWithEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsUserWithEmail(email));
    }

    @GetMapping("/panelsOf/{nickname}")
    public ResponseEntity<User> getPanelsOfUserByNickname(@PathVariable String nickname) {
        return null;
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

    @PostMapping(value = "/editProfile", consumes = "multipart/form-data")
    public ResponseEntity<Boolean> editProfile(@RequestPart("user") String json, @RequestPart(value = "image", required = false) MultipartFile image, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId, HttpServletRequest request) throws IOException {
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //User convert
        User user = mapper.treeToValue(mapper.readTree(json), User.class);

        //Image
        if(image != null) {
            String imageName = imageStorageService.save(image);
            user.setProfilePicture(imageStorageService.getUrl(imageName,request));
        }
        else user.setProfilePicture(null);

        userService.update(user);
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/UpdatePlan", consumes = "multipart/form-data")
    public ResponseEntity<Boolean> updatePlan(@RequestPart("user") String json, @RequestPart("isMonthly") String isMonthly, @CookieValue(value = "SESSIONID", defaultValue = "") String sessionId, HttpServletRequest request) throws IOException {
        if(sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = mapper.treeToValue(mapper.readTree(json), User.class);

        return ResponseEntity.ok(userService.updatePlan(user, isMonthly.equals("true")));
    }

    @PostMapping("/signIn")
    public ResponseEntity<Map<String, String>> signIn(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {

        Map<String, String> errors = userService.signIn(email, password);
        if(errors.containsKey("errors") && errors.get("errors") == null){
            User user = userService.findByEmail(email);
            Cookie cookie = cookieService.createSessionCookie();
            sessionService.update(cookie.getValue(), user);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(errors);
    }

    @PostMapping("/signOut")
    public ResponseEntity<String> signOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie cookieSession = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equalsIgnoreCase("SESSIONID")) cookieSession = cookie;
            }
            cookieSession.setPath("/");
            cookieSession = cookieService.deleteSession(cookieSession);
            response.addCookie(cookieSession);
        }
        return ResponseEntity.ok("");
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
