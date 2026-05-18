package com.backSpotic.backSpotic.dto.spotify;

import lombok.Data;
import java.util.List;

@Data
public class NewReleasesResponseDto {
    private Albums albums;

    @Data
    public static class Albums{
        private List<AlbumItemDto> items;
    }
}
