package com.backSpotic.backSpotic.dto.frontEnd;
import lombok.Data;

@Data
public class FrontendArtistDto {
    private String id;
    private String name;
    private String imageUrl;
    private String spotifyUrl;
    private int followers;
}
