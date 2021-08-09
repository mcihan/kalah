package com.cihan.kalah.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@Schema(name = "MoveGameResponse", description = "Example Move Game Response")
public class MoveGameResponse {
    private String id;
    private String url;
    private Map<Integer, Integer> status;
}
