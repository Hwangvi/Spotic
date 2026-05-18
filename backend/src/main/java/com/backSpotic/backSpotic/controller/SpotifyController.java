package com.backSpotic.backSpotic.controller;

import com.backSpotic.backSpotic.dto.frontEnd.*;
import com.backSpotic.backSpotic.dto.spotify.*;
import com.backSpotic.backSpotic.mapper.SpotifyMapper;
import com.backSpotic.backSpotic.service.SpotifyApiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyApiService spotifyApiService;

    @Autowired
    private SpotifyMapper spotifyMapper;

    @GetMapping("/new-releases")
    public ResponseEntity<List<FrontendAlbumDto>> getNewReleases(HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        List<AlbumItemDto> rawAlbums = spotifyApiService.getNewReleases(accessToken).getAlbums().getItems();

        return ResponseEntity.ok(spotifyMapper.toFrontendAlbumList(rawAlbums));
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<FrontendArtistDto> getArtist(@PathVariable String id, HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        ArtistDto rawArtist = spotifyApiService.getArtist(id, accessToken);

        return ResponseEntity.ok(spotifyMapper.toFrontendArtist(rawArtist));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FrontendArtistDto>> searchArtists(@RequestParam String term, HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        List<ArtistDto> rawArtists = spotifyApiService.searchArtists(term, accessToken).getArtists().getItems();

        return ResponseEntity.ok(spotifyMapper.toFrontendArtistList(rawArtists));
    }

    @GetMapping("/artists/{id}/top-tracks")
    public ResponseEntity<List<FrontendTrackDto>> getTopTracks(@PathVariable String id, HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        List<TrackDto> rawTracks = spotifyApiService.getTopTracks(id, accessToken).getTracks();

        return ResponseEntity.ok(spotifyMapper.toFrontendTrackList(rawTracks));
    }

    @GetMapping("/me")
    public ResponseEntity<FrontendUserDto> getCurrentUserProfile(HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        UserProfileDto rawUser = spotifyApiService.getCurrentUserProfile(accessToken);

        return ResponseEntity.ok(spotifyMapper.toFrontendUser(rawUser));
    }

    @GetMapping("/me/playlists")
    public ResponseEntity<List<FrontendPlaylistDto>> getUserPlaylists(HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        List<PlaylistDto> rawPlaylists = spotifyApiService.getUserPlaylists(accessToken).getItems();

        return ResponseEntity.ok(spotifyMapper.toFrontendPlaylistList(rawPlaylists));
    }

    @GetMapping("/me/top/tracks")
    public ResponseEntity<List<FrontendTrackDto>> getUserTopTracks(HttpSession session) {
        String accessToken = getAccessTokenFromSession(session);
        List<TrackDto> rawTracks = spotifyApiService.getUserTopTracks(accessToken).getItems();

        return ResponseEntity.ok(spotifyMapper.toFrontendTrackList(rawTracks));
    }

    private String getAccessTokenFromSession(HttpSession session) {
        String accessToken = (String) session.getAttribute("spotify_access_token");
        if (accessToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No está autenticado con Spotify.");
        }
        return accessToken;
    }
}