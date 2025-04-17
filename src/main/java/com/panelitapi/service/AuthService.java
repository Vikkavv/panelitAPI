package com.panelitapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String testAndEncodePassword(String password) {
        Pattern pattern = Pattern.compile("^[-A-Za-z0-9ÑñÇç@_.&%$,]{8,12}$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.find()) throw new RuntimeException("Password has to be between 8 and 12 characters long and must include common letters and symbols");
        return encodePassword(password);
    }

    public boolean validatePassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
