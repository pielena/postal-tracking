package com.github.pielena.postal.tracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pielena.postal.tracking.persistence.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OperationDto {

    private UUID id;
    private int postOfficeIndex;
    private PostOfficeType postOfficeType;
    private State state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
}
