package com.backSpotic.backSpotic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.backSpotic.backSpotic.dto.TokenResponseDto;

@Service
public class SpotifyAuthService {
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    private final String scope = "user-read-private user-read-email playlist-read-private user-top-read";

    private final RestTemplate restTemplate;


    public SpotifyAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String getAuthorizationURL() {

        return "https://accounts.spotify.com/authorize?" +
                "client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope +
                "&show_dialog=true";
    }

    public TokenResponseDto exchangeCodeForToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<TokenResponseDto> response = restTemplate.postForEntity(
                    "https://accounts.spotify.com/api/token",
                    requestEntity,
                    TokenResponseDto.class
            );
            return response.getBody();

        } catch (RestClientException e) {
            return null;
        }
    }
}