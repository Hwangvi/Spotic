package com.backSpotic.backSpotic.controller;
import com.backSpotic.backSpotic.dto.frontEnd.*;
import com.backSpotic.backSpotic.dto.spotify.*;
import com.backSpotic.backSpotic.mapper.SpotifyMapper;
import com.backSpotic.backSpotic.service.SpotifyApiService;
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
    public ResponseEntity<List<FrontendAlbumDto>> getNewReleases(@RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        List<AlbumItemDto> rawAlbums = spotifyApiService.getNewReleases(accessToken).getAlbums().getItems();
        return ResponseEntity.ok(spotifyMapper.toFrontendAlbumList(rawAlbums));
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<FrontendArtistDto> getArtist(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        ArtistDto rawArtist = spotifyApiService.getArtist(id, accessToken);
        return ResponseEntity.ok(spotifyMapper.toFrontendArtist(rawArtist));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FrontendArtistDto>> searchArtists(@RequestParam String term, @RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        List<ArtistDto> rawArtists = spotifyApiService.searchArtists(term, accessToken).getArtists().getItems();
        return ResponseEntity.ok(spotifyMapper.toFrontendArtistList(rawArtists));
    }

    @GetMapping("/artists/{id}/top-tracks")
    public ResponseEntity<List<FrontendTrackDto>> getTopTracks(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        List<TrackDto> rawTracks = spotifyApiService.getTopTracks(id, accessToken).getTracks();
        return ResponseEntity.ok(spotifyMapper.toFrontendTrackList(rawTracks));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUserProfile(@RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        UserProfileDto profile = spotifyApiService.getCurrentUserProfile(accessToken);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me/playlists")
    public ResponseEntity<List<FrontendPlaylistDto>> getUserPlaylists(@RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        List<PlaylistDto> rawPlaylists = spotifyApiService.getUserPlaylists(accessToken).getItems();
        return ResponseEntity.ok(spotifyMapper.toFrontendPlaylistList(rawPlaylists));
    }

    @GetMapping("/me/top/tracks")
    public ResponseEntity<List<FrontendTrackDto>> getUserTopTracks(@RequestHeader("Authorization") String authHeader) {
        String accessToken = extractToken(authHeader);
        List<TrackDto> rawTracks = spotifyApiService.getUserTopTracks(accessToken).getItems();
        return ResponseEntity.ok(spotifyMapper.toFrontendTrackList(rawTracks));
    }

    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No está autenticado con Spotify.");
        }
        String token = authHeader.substring(7).trim();
        if (token.startsWith("\"") && token.endsWith("\"")) {
            token = token.substring(1, token.length() - 1);
        }
        return token;
    }
}