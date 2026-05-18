package com.backSpotic.backSpotic.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class OwnerDto {
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("external_urls")
    private ExternalUrlsDto externalUrls;
    private String href;
    private String id;
    private String type;
    private String uri;
}
