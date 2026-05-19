package com.backSpotic.backSpotic.service;

import com.backSpotic.backSpotic.dto.spotify.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("[MODO DEMO] Interceptado fallo de Spotify en " + url + ". Cargando Datos Demo.");
            return (T) getMockObjectForClass(responseType);
        }
    }

    private <T> T performRequestWithGeneric(String url, HttpMethod method, String accessToken, ParameterizedTypeReference<T> responseType) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(createAuthHeaders(accessToken));
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("[MODO DEMO GENÉRICO] Interceptado fallo en " + url + ". Cargando Listas Demo.");

            PageResponseDto<Object> mockPage = new PageResponseDto<>();
            List<Object> items = new ArrayList<>();

            if (url.contains("playlists")) {
                PlaylistDto p1 = new PlaylistDto(); p1.setId("p1"); p1.setName("Mis Favoritas de Siempre");
                PlaylistDto p2 = new PlaylistDto(); p2.setId("p2"); p2.setName("Descubrimiento Semanal (Demo)");
                PlaylistDto p3 = new PlaylistDto(); p3.setId("p3"); p3.setName("Chill & Code ☕");
                items.add(p1); items.add(p2); items.add(p3);
            } else if (url.contains("top/tracks")) {
                TrackDto t1 = createMockTrack("t1", "La Bachata", "Manuel Turizo", "https://open.spotify.com/embed/track/5wwv8Up7v96m83Uu9f76Yn");
                TrackDto t2 = createMockTrack("t2", "Despechá", "Rosalía", "");
                TrackDto t3 = createMockTrack("t3", "Columbia", "Quevedo", "");
                items.add(t1); items.add(t2); items.add(t3);
            }

            mockPage.setItems((List) items);
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
            NewReleasesResponseDto.Albums mockAlbums = new NewReleasesResponseDto.Albums();
            List<AlbumItemDto> albumList = new ArrayList<>();

            AlbumItemDto a1 = new AlbumItemDto(); a1.setId("alb1"); a1.setName("Motomami"); a1.setReleaseDate("2022"); a1.setImages(createMockImages());
            AlbumItemDto a2 = new AlbumItemDto(); a2.setId("alb2"); a2.setName("Donde nacen los infiernos"); a2.setReleaseDate("2026"); a2.setImages(createMockImages());
            AlbumItemDto a3 = new AlbumItemDto(); a3.setId("alb3"); a3.setName("Estopa (25 Aniversario)"); a3.setReleaseDate("2024"); a3.setImages(createMockImages());

            albumList.add(a1); albumList.add(a2); albumList.add(a3);
            mockAlbums.setItems(albumList);
            mockReleases.setAlbums(mockAlbums);
            return mockReleases;
        }

        if (responseType == SearchArtistResponseDto.class) {
            SearchArtistResponseDto mockSearch = new SearchArtistResponseDto();
            SearchArtistResponseDto.Artists mockArtists = new SearchArtistResponseDto.Artists();
            List<ArtistDto> artistList = new ArrayList<>();

            artistList.add(createMockArtist("art1", "Estopa"));
            artistList.add(createMockArtist("art2", "Rosalía"));
            artistList.add(createMockArtist("art3", "Melendi"));
            artistList.add(createMockArtist("art4", "Quevedo"));

            mockArtists.setItems(artistList);
            mockSearch.setArtists(mockArtists);
            return mockSearch;
        }

        if (responseType == TopTracksResponseDto.class) {
            TopTracksResponseDto mockTop = new TopTracksResponseDto();
            List<TrackDto> trackList = new ArrayList<>();

            trackList.add(createMockTrack("tr1", "Como Camarón", "Estopa", ""));
            trackList.add(createMockTrack("tr2", "Tu Calorro", "Estopa", ""));
            trackList.add(createMockTrack("tr3", "Pastillas de Freno", "Estopa", ""));

            mockTop.setTracks(trackList);
            return mockTop;
        }

        if (responseType == ArtistDto.class) {
            return createMockArtist("art_clicked", "Artista en Modo Demo");
        }

        try {
            return responseType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private ArtistDto createMockArtist(String id, String name) {
        ArtistDto artist = new ArtistDto();
        artist.setId(id);
        artist.setName(name);
        artist.setType("artist");
        artist.setImages(createMockImages());
        return artist;
    }

    private TrackDto createMockTrack(String id, String name, String artistName, String previewUrl) {
        TrackDto track = new TrackDto();
        track.setId(id);
        track.setName(name);
        track.setPreviewUrl(previewUrl != "" ? previewUrl : null);

        ArtistDto mockArtistForTrack = new ArtistDto();
        mockArtistForTrack.setId("simple_" + id);
        mockArtistForTrack.setName(artistName);
        mockArtistForTrack.setType("artist");
        mockArtistForTrack.setImages(new ArrayList<>());

        track.setArtists(List.of(mockArtistForTrack));

        return track;
    }

    private List<ImageDto> createMockImages() {
        List<ImageDto> images = new ArrayList<>();
        ImageDto img = new ImageDto();
        img.setUrl("https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=300");
        img.setHeight(300);
        img.setWidth(300);
        images.add(img);
        return images;
    }
}