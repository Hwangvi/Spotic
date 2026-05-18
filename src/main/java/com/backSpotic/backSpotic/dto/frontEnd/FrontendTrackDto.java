package com.backSpotic.backSpotic.dto.frontEnd;

import lombok.Data;

@Data
public class FrontendTrackDto {
    private String id;
    private String name;
    private String artistName;
    private String albumImage;
    private String previewUrl;
    private int durationMs;
}
