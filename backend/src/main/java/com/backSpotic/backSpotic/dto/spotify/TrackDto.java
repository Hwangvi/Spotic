package com.backSpotic.backSpotic.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class TrackDto {
    private AlbumItemDto album;
    private List<ArtistDto> artists;
    @JsonProperty("duration_ms")
    private int durationMs;
    private boolean explicit;
    private String id;
    private String name;
    private int popularity;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("track_number")
    private int trackNumber;
    private String uri;
}
