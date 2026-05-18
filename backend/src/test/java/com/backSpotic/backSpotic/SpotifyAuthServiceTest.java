package com.backSpotic.backSpotic;

import com.backSpotic.backSpotic.dto.TokenResponseDto;
import com.backSpotic.backSpotic.service.SpotifyAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpotifyAuthServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SpotifyAuthService spotifyAuthService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(spotifyAuthService, "clientId", "fake-client-id");
        ReflectionTestUtils.setField(spotifyAuthService, "clientSecret", "fake-secret");
        ReflectionTestUtils.setField(spotifyAuthService, "redirectUri", "http://localhost:4200/callback");
    }

    @Test
    @DisplayName("Debe generar la URL de autorización con los parámetros correctos")
    void getAuthorizationURL_Test() {
        String url = spotifyAuthService.getAuthorizationURL();

        assertNotNull(url);
        assertTrue(url.contains("client_id=fake-client-id"));
        assertTrue(url.contains("redirect_uri=http://localhost:4200/callback"));
    }

    @Test
    @DisplayName("Debe retornar un TokenResponseDto cuando la API responde OK")
    void exchangeCodeForToken_Success() {
        TokenResponseDto mockDto = new TokenResponseDto();
        mockDto.setAccessToken("token-super-secreto");
        mockDto.setRefreshToken("refresh-token-xyz");
        mockDto.setExpiresIn(3600);
        mockDto.setTokenType("Bearer");

        when(restTemplate.postForEntity(
                eq("https://accounts.spotify.com/api/token"),
                any(HttpEntity.class),
                eq(TokenResponseDto.class))
        ).thenReturn(new ResponseEntity<>(mockDto, HttpStatus.OK));

        TokenResponseDto resultado = spotifyAuthService.exchangeCodeForToken("codigo-valido");

        assertNotNull(resultado);
        assertEquals("token-super-secreto", resultado.getAccessToken());
        assertEquals(3600, resultado.getExpiresIn());
    }

    @Test
    @DisplayName("Debe retornar null si la API de Spotify falla")
    void exchangeCodeForToken_Failure() {
        when(restTemplate.postForEntity(anyString(), any(), eq(TokenResponseDto.class)))
                .thenThrow(new RestClientException("Spotify caído"));

        TokenResponseDto resultado = spotifyAuthService.exchangeCodeForToken("codigo-invalido");

        assertNull(resultado);
    }
}