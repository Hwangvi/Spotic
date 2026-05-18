package com.backSpotic.backSpotic.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class AlbumItemDto {
    @JsonProperty("album_type")
    private String albumType;
    private List<SimpleArtistDto> artists;
    private String id;
    private List<ImageDto> images;
    private String name;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("total_tracks")
    private int totalTracks;
    private String uri;
    @JsonProperty("external_urls")
    private ExternalUrlsDto externalUrls;
}
