package com.cihan.kalah.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "StartGameResponse", description = "Example Start Game Response")
public class StartGameResponse {
    private String id;
    private String uri;
}
