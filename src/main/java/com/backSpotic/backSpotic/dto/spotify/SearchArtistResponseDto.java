package com.backSpotic.backSpotic.dto.spotify;

import lombok.Data;
import java.util.List;
@Data
public class SearchArtistResponseDto {
    private Artists artists;
    @Data
    public static class Artists {
        private List<ArtistDto> items;
    }
}
