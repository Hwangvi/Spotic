package com.backSpotic.backSpotic;

import com.backSpotic.backSpotic.dto.spotify.*;
import com.backSpotic.backSpotic.service.SpotifyApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpotifyApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SpotifyApiService spotifyApiService;

    private final String TOKEN = "mock-token";

    @Test
    @DisplayName("getNewReleases funciona correctamente")
    void getNewReleases_Success() {
        NewReleasesResponseDto responseDto = new NewReleasesResponseDto();
        when(restTemplate.exchange(
                contains("/browse/new-releases"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(NewReleasesResponseDto.class))
        ).thenReturn(new ResponseEntity<>(responseDto, HttpStatus.OK));

        NewReleasesResponseDto result = spotifyApiService.getNewReleases(TOKEN);

        assertNotNull(result);
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), eq(NewReleasesResponseDto.class));
    }

    @Test
    @DisplayName("getUserPlaylists maneja genéricos (PageResponseDto)")
    void getUserPlaylists_Success() {
        PageResponseDto<PlaylistDto> pageDto = new PageResponseDto<>();
        pageDto.setItems(Collections.emptyList());

        when(restTemplate.exchange(
                contains("/me/playlists"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))
        ).thenReturn(new ResponseEntity<>(pageDto, HttpStatus.OK));

        PageResponseDto<PlaylistDto> result = spotifyApiService.getUserPlaylists(TOKEN);

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
    }
    @Test
    @DisplayName("getArtist devuelve un artista correctamente")
    void getArtist_Success() {
        String artistId = "0TnOYISbd1XYRBk9myaseg";
        ArtistDto mockArtist = new ArtistDto();


        when(restTemplate.exchange(
                contains("/artists/" + artistId),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ArtistDto.class))
        ).thenReturn(new ResponseEntity<>(mockArtist, HttpStatus.OK));

        ArtistDto result = spotifyApiService.getArtist(artistId, TOKEN);

        assertNotNull(result);
    }

    @Test
    @DisplayName("searchArtists construye la URL y devuelve resultados")
    void searchArtists_Success() {
        String term = "Shakira";
        SearchArtistResponseDto mockResponse = new SearchArtistResponseDto();

        when(restTemplate.exchange(
                contains("/search"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SearchArtistResponseDto.class))
        ).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        SearchArtistResponseDto result = spotifyApiService.searchArtists(term, TOKEN);

        assertNotNull(result);
    }

    @Test
    @DisplayName("getTopTracks devuelve las canciones top")
    void getTopTracks_Success() {
        String artistId = "123";
        TopTracksResponseDto mockResponse = new TopTracksResponseDto();

        when(restTemplate.exchange(
                contains("/artists/" + artistId + "/top-tracks"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(TopTracksResponseDto.class))
        ).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        TopTracksResponseDto result = spotifyApiService.getTopTracks(artistId, TOKEN);

        assertNotNull(result);
    }

    @Test
    @DisplayName("getCurrentUserProfile devuelve el perfil del usuario")
    void getCurrentUserProfile_Success() {
        UserProfileDto mockProfile = new UserProfileDto();

        when(restTemplate.exchange(
                contains("/me"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserProfileDto.class))
        ).thenReturn(new ResponseEntity<>(mockProfile, HttpStatus.OK));

        UserProfileDto result = spotifyApiService.getCurrentUserProfile(TOKEN);

        assertNotNull(result);
    }

    @Test
    @DisplayName("getUserTopTracks maneja genéricos correctamente")
    void getUserTopTracks_Success() {
        PageResponseDto<TrackDto> mockPage = new PageResponseDto<>();
        mockPage.setItems(Collections.emptyList());

        when(restTemplate.exchange(
                contains("/me/top/tracks"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))
        ).thenReturn(new ResponseEntity<>(mockPage, HttpStatus.OK));

        PageResponseDto<TrackDto> result = spotifyApiService.getUserTopTracks(TOKEN);

        assertNotNull(result);
    }
}