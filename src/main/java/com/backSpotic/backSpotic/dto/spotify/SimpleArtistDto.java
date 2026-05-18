package com.backSpotic.backSpotic.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class SimpleArtistDto {
    @JsonProperty("external_urls")
    private ExternalUrlsDto externalUrls;
    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;
}
