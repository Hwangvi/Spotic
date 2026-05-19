package com.backSpotic.backSpotic.controller;

import com.backSpotic.backSpotic.service.SpotifyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backSpotic.backSpotic.dto.TokenResponseDto;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SpotifyAuthService spotifyAuthService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String authorizationURL = spotifyAuthService.getAuthorizationURL();
        response.sendRedirect(authorizationURL);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        TokenResponseDto tokenDto = spotifyAuthService.exchangeCodeForToken(code);
        if (tokenDto != null) {
            response.sendRedirect(frontendUrl + "/home?token=" + tokenDto.getAccessToken());
        } else {
            response.sendRedirect(frontendUrl + "/login-error");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}