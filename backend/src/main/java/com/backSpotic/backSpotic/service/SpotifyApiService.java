package com.backSpotic.backSpotic.service;

import com.backSpotic.backSpotic.dto.spotify.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import java.util.ArrayList;

@Service
public class SpotifyApiService {

    private final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";
    private final RestTemplate restTemplate;

    public SpotifyApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NewReleasesResponseDto getNewReleases(String accessToken) {
        String url = SPOTIFY_API_BASE_URL + "/browse/new-releases";
        return performRequest(url, HttpMethod.GET, accessToken, NewReleasesResponseDto.class);
    }

    public ArtistDto getArtist(String id, String accessToken) {
        String url = SPOTIFY_API_BASE_URL + "/artists/" + id;
        return performRequest(url, HttpMethod.GET, accessToken, ArtistDto.class);
    }

    public SearchArtistResponseDto searchArtists(String term, String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(SPOTIFY_API_BASE_URL + "/search")
                .queryParam("q", term)
                .queryParam("type", "artist")
                .queryParam("limit", 15)
                .toUriString();
        return performRequest(url, HttpMethod.GET, accessToken, SearchArtistResponseDto.class);
    }

    public TopTracksResponseDto getTopTracks(String artistId, String accessToken) {
        String url = SPOTIFY_API_BASE_URL + "/artists/" + artistId + "/top-tracks?market=es";
        return performRequest(url, HttpMethod.GET, accessToken, TopTracksResponseDto.class);
    }

    public UserProfileDto getCurrentUserProfile(String accessToken) {
        String url = SPOTIFY_API_BASE_URL + "/me";
        return performRequest(url, HttpMethod.GET, accessToken, UserProfileDto.class);
    }

    public PageResponseDto<PlaylistDto> getUserPlaylists(String accessToken) {
        String url = SPOTIFY_API_BASE_URL + "/me/playlists";
        return performRequestWithGeneric(url, HttpMethod.GET, accessToken, new ParameterizedTypeReference<PageResponseDto<PlaylistDto>>() {});
    }

    public PageResponseDto<TrackDto> getUserTopTracks(String accessToken) {
        String url = SPOTIFY_API_BASE_URL + "/me/top/tracks";
        return performRequestWithGeneric(url, HttpMethod.GET, accessToken, new ParameterizedTypeReference<PageResponseDto<TrackDto>>() {});
    }

    private <T> T performRequest(String url, HttpMethod method, String accessToken, Class<T> responseType) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(createAuthHeaders(accessToken));
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("[MODO DEMO] Falló la petición a " + url + " -> " + e.getMessage());
            return (T) getMockObjectForClass(responseType);
        }
    }

    private <T> T performRequestWithGeneric(String url, HttpMethod method, String accessToken, ParameterizedTypeReference<T> responseType) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(createAuthHeaders(accessToken));
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("[MODO DEMO GENÉRICO] Falló la petición a " + url + " -> " + e.getMessage());
            PageResponseDto<?> mockPage = new PageResponseDto<>();
            mockPage.setItems(new ArrayList<>());
            return (T) mockPage;
        }
    }

    private HttpHeaders createAuthHeaders(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private Object getMockObjectForClass(Class<?> responseType) {
        if (responseType == UserProfileDto.class) {
            UserProfileDto mockProfile = new UserProfileDto();
            mockProfile.setId("demo_portfolio_user");
            mockProfile.setDisplayName("Invitado Premium (Demo)");
            mockProfile.setEmail("portfolio@spotic.com");
            return mockProfile;
        }

        if (responseType == NewReleasesResponseDto.class) {
            NewReleasesResponseDto mockReleases = new NewReleasesResponseDto();
            return mockReleases;
        }

        if (responseType == SearchArtistResponseDto.class) {
            SearchArtistResponseDto mockSearch = new SearchArtistResponseDto();
            return mockSearch;
        }

        try {
            return responseType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}