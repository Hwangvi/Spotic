package com.backSpotic.backSpotic.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class UserProfileDto {
    private String country;
    @JsonProperty("display_name")
    private String displayName;
    private String email;
    @JsonProperty("external_urls")
    private ExternalUrlsDto externalUrls;
    private FollowersDto followers;
    private String id;
    private List<ImageDto> images;
    private String product;
    private String uri;
}
