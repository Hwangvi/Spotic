package com.backSpotic.backSpotic.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class PlaylistDto {
    private boolean collaborative;
    private String description;
    private String id;
    private List<ImageDto> images;
    private String name;
    private OwnerDto owner;
    @JsonProperty("snapshot_id")
    private String snapshotId;
    private String uri;
    @JsonProperty("external_urls")
    private ExternalUrlsDto externalUrls;
}
