package com.github.pielena.postal.tracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pielena.postal.tracking.enums.PostOfficeType;
import com.github.pielena.postal.tracking.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Operation DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OperationDto {

    @Schema(description = "operation id", example = "c8cd3aee-62af-4239-a5da-23965734a733",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "current post office index", example = "123456")
    private int postOfficeIndex;

    @Schema(description = "current post office type", example = "TRANSIT")
    private PostOfficeType postOfficeType;

    @Schema(description = "postal item state", example = "ARRIVED")
    private State state;

    @Schema(type = "string", description = "operation registration date", example = "20-09-2023 19:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
}
