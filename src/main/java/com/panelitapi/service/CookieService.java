package com.panelitapi.service;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CookieService {

    public Cookie createSessionCookie(Boolean rememberMe){
        Cookie cookie = new Cookie("SESSIONID", UUID.randomUUID().toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(/*true*/ false);
        cookie.setPath("/");
        if(rememberMe){
            cookie.setMaxAge(10/*Days*/ * 24/*Hours*/ * 60/*Minutes*/ * 60/*Seconds*/);
        }
        return cookie;
    }

    public Cookie createSessionCookie(){
        return createSessionCookie(false);
    }

    public Cookie deleteSession(Cookie cookie){
        Cookie cookieForDelete = new Cookie(cookie.getName(), cookie.getValue());
        cookieForDelete.setHttpOnly(true);
        cookieForDelete.setPath(cookie.getPath());
        cookieForDelete.setMaxAge(0);
        return cookieForDelete;
    }
}
