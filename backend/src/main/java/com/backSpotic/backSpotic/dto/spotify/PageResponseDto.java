package com.backSpotic.backSpotic.dto.spotify;

import lombok.Data;
import java.util.List;
@Data
public class PageResponseDto<T> {
    private List<T> items;
}