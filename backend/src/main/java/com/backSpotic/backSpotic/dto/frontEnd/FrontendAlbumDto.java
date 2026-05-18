package com.backSpotic.backSpotic.dto.frontEnd;

import lombok.Data;

@Data
public class FrontendAlbumDto {
    private String id;
    private String name;
    private String artistName;
    private String imageUrl;
    private String artistId;
    private String releaseDate;
    private int totalTracks;
    private String spotifyUrl;
}
