package com.backSpotic.backSpotic.dto.frontEnd;

import lombok.Data;

@Data
public class FrontendUserDto {
    private String id;
    private String displayName;
    private String email;
    private String country;
    private String imageUrl;
    private String spotifyUrl;
    private int totalFollowers;
}
