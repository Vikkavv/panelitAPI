package com.panelitapi.service;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CookieService {

    public Cookie createSessionCookie(){
        Cookie cookie = new Cookie("SESSIONID", UUID.randomUUID().toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(/*true*/ false);
        cookie.setPath("/");
        return cookie;
    }
}
