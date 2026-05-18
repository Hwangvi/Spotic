package com.backSpotic.backSpotic.dto.frontEnd;

import lombok.Data;

@Data
public class FrontendPlaylistDto {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String ownerName;
    private String spotifyUrl;
}
