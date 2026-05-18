package com.backSpotic.backSpotic.controller;

import com.backSpotic.backSpotic.service.SpotifyAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backSpotic.backSpotic.dto.TokenResponseDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SpotifyAuthService spotifyAuthService;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String authorizationURL = spotifyAuthService.getAuthorizationURL();
        response.sendRedirect(authorizationURL);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code, HttpSession session, HttpServletResponse response) throws IOException {
        TokenResponseDto tokenDto = spotifyAuthService.exchangeCodeForToken(code);
        if (tokenDto != null) {
            session.setAttribute("spotify_access_token", tokenDto.getAccessToken());
            response.sendRedirect("http://localhost:4200/home");
        } else {
            response.sendRedirect("http://localhost:4200/login-error");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().build();
    }
}