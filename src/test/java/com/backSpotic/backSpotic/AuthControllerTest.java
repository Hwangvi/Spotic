package com.backSpotic.backSpotic;

import com.backSpotic.backSpotic.controller.AuthController;
import com.backSpotic.backSpotic.dto.TokenResponseDto;
import com.backSpotic.backSpotic.service.SpotifyAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpotifyAuthService spotifyAuthService;

    @Test
    @DisplayName("Login debe redirigir a la URL de Spotify")
    void login_ShouldRedirect() throws Exception {
        String expectedUrl = "https://accounts.spotify.com/authorize?etc...";
        when(spotifyAuthService.getAuthorizationURL()).thenReturn(expectedUrl);

        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(expectedUrl));
    }

    @Test
    @DisplayName("Callback Exitoso: Guarda token en sesión y redirige al Home")
    void callback_Success() throws Exception {

        TokenResponseDto mockToken = new TokenResponseDto();
        mockToken.setAccessToken("token-super-secreto");

        when(spotifyAuthService.exchangeCodeForToken(anyString())).thenReturn(mockToken);

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/api/auth/callback")
                        .param("code", "codigo-valido-spotify")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:4200/home"));

        assertEquals("token-super-secreto", session.getAttribute("spotify_access_token"));
    }

    @Test
    @DisplayName("Callback Fallido: Redirige a Login-Error")
    void callback_Failure() throws Exception {
        when(spotifyAuthService.exchangeCodeForToken(anyString())).thenReturn(null);

        mockMvc.perform(get("/api/auth/callback")
                        .param("code", "codigo-invalido"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:4200/login-error"));
    }

    @Test
    @DisplayName("Logout debe invalidar la sesión")
    void logout_ShouldInvalidateSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("algo", "valor");

        mockMvc.perform(post("/api/auth/logout")
                        .session(session))
                .andExpect(status().isOk());

        assertTrue(session.isInvalid());
    }
}
