package com.backSpotic.backSpotic;

import com.backSpotic.backSpotic.controller.SpotifyController;
import com.backSpotic.backSpotic.dto.spotify.*;
import com.backSpotic.backSpotic.service.SpotifyApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpotifyController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SportifyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpotifyApiService spotifyApiService;
    private MockHttpSession session;
    private final String FAKE_TOKEN  = "fake_token_123";

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
        session.setAttribute("spotify_access_token",FAKE_TOKEN);
    }

    @Test
    @DisplayName("Si no hay tokens de session debe devolver un 401")
    void requestWithoutSessionReturns401() throws Exception {
        mockMvc.perform(get("/api/spotify/me")).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /new-releases devuelve un 200 OK")
    void getNewReleasesSuccess() throws Exception {
        NewReleasesResponseDto response = new NewReleasesResponseDto();
        NewReleasesResponseDto.Albums albums = new NewReleasesResponseDto.Albums();
        albums.setItems(Collections.emptyList());
        response.setAlbums(albums);

        when(spotifyApiService.getNewReleases(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/spotify/new-releases").session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    @Test
    @DisplayName("GET /search devuelve 200 OK")
    void searchArtists_Success() throws Exception {
        SearchArtistResponseDto response = new SearchArtistResponseDto();
        SearchArtistResponseDto.Artists artists = new SearchArtistResponseDto.Artists();
        artists.setItems(Collections.emptyList());
        response.setArtists(artists);

        when(spotifyApiService.searchArtists(eq("shakira"), anyString())).thenReturn(response);


        mockMvc.perform(get("/api/spotify/search")
                        .param("term", "shakira")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /artists/{id}/top-tracks devuelve 200 OK")
    void getTopTracks_Success() throws Exception {
        TopTracksResponseDto response = new TopTracksResponseDto();
        response.setTracks(Collections.emptyList());

        when(spotifyApiService.getTopTracks(eq("123"), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/spotify/artists/123/top-tracks")
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /me/playlists devuelve 200 OK")
    void getUserPlaylists_Success() throws Exception {
        PageResponseDto<PlaylistDto> response = new PageResponseDto<>();
        response.setItems(Collections.emptyList());

        when(spotifyApiService.getUserPlaylists(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/spotify/me/playlists")
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /me/top/tracks devuelve 200 OK")
    void getUserTopTracks_Success() throws Exception {
        PageResponseDto<TrackDto> response = new PageResponseDto<>();
        response.setItems(Collections.emptyList());

        when(spotifyApiService.getUserTopTracks(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/spotify/me/top/tracks")
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /artists/{id} devuelve 200 OK (Objeto simple)")
    void getArtist_Success() throws Exception {
        ArtistDto artist = new ArtistDto();

        when(spotifyApiService.getArtist(eq("123"), anyString())).thenReturn(artist);

        mockMvc.perform(get("/api/spotify/artists/123")
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /me devuelve 200 OK")
    void getCurrentUserProfile_Success() throws Exception {
        UserProfileDto profile = new UserProfileDto();

        when(spotifyApiService.getCurrentUserProfile(anyString())).thenReturn(profile);

        mockMvc.perform(get("/api/spotify/me")
                        .session(session))
                .andExpect(status().isOk());
    }


}
