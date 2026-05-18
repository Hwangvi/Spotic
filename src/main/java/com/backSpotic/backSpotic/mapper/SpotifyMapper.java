package com.backSpotic.backSpotic.mapper;

import com.backSpotic.backSpotic.dto.frontEnd.*;
import com.backSpotic.backSpotic.dto.spotify.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotifyMapper {

    @Mapping(target = "spotifyUrl", source = "externalUrls.spotify")
    @Mapping(target = "followers", source = "followers.total")
    @Mapping(target = "imageUrl", source = "images", qualifiedByName = "getFirstImageUrl")
    FrontendArtistDto toFrontendArtist(ArtistDto artistDto);

    List<FrontendArtistDto> toFrontendArtistList(List<ArtistDto> artists);

    @Mapping(target = "artistName", source = "artists", qualifiedByName = "getFirstArtistName")
    @Mapping(target = "albumImage", source = "album.images", qualifiedByName = "getFirstImageUrl")
    FrontendTrackDto toFrontendTrack(TrackDto trackDto);

    List<FrontendTrackDto> toFrontendTrackList(List<TrackDto> tracks);

    @Mapping(target = "spotifyUrl", source = "externalUrls.spotify")
    @Mapping(target = "totalFollowers", source = "followers.total")
    @Mapping(target = "imageUrl", source = "images", qualifiedByName = "getFirstImageUrl")
    FrontendUserDto toFrontendUser(UserProfileDto userDto);

    @Mapping(target = "spotifyUrl", source = "externalUrls.spotify")
    @Mapping(target = "ownerName", source = "owner.displayName")
    @Mapping(target = "imageUrl", source = "images", qualifiedByName = "getFirstImageUrl")
    FrontendPlaylistDto toFrontendPlaylist(PlaylistDto playlistDto);

    List<FrontendPlaylistDto> toFrontendPlaylistList(List<PlaylistDto> playlists);


    @Mapping(target = "artistName", source = "artists", qualifiedByName = "getFirstSimpleArtistName")
    @Mapping(target = "imageUrl", source = "images", qualifiedByName = "getFirstImageUrl")
    @Mapping(target = "spotifyUrl", source = "externalUrls.spotify")
    @Mapping(target = "artistId", source = "artists", qualifiedByName = "getFirstArtistId")
    FrontendAlbumDto toFrontendAlbum(AlbumItemDto albumDto);

    List<FrontendAlbumDto> toFrontendAlbumList(List<AlbumItemDto> albums);

    @Named("getFirstImageUrl")
    default String getFirstImageUrl(List<ImageDto> images) {
        if (images != null && !images.isEmpty()) {
            return images.get(0).getUrl();
        }
        return null;
    }

    @Named("getFirstArtistName")
    default String getFirstArtistName(List<ArtistDto> artists) {
        if (artists != null && !artists.isEmpty()) {
            return artists.get(0).getName();
        }
        return "Unknown Artist";
    }

    @Named("getFirstSimpleArtistName")
    default String getFirstSimpleArtistName(List<SimpleArtistDto> artists) {
        if (artists != null && !artists.isEmpty()) {
            return artists.get(0).getName();
        }
        return "Unknown Artist";
    }

    @Named("getFirstArtistId")
    default String getFirstArtistId(List<SimpleArtistDto> artists) {
        if (artists != null && !artists.isEmpty()) {
            return artists.get(0).getId();
        }
        return null;
    }
}
