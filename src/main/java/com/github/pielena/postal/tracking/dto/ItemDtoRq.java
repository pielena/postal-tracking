package com.github.pielena.postal.tracking.dto;

import com.github.pielena.postal.tracking.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "Request Postal item DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemDtoRq {

    @Schema(description = "postal item id", example = "c8cd3aee-62af-4239-a5da-23965734a733")
    private UUID id;

    @Schema(description = "postal item type", example = "LETTER")
    private ItemType type;

    @Schema(description = "sender post office index", example = "123456")
    private int senderIndex;

    @Schema(description = "sender name", example = "Ivan Ivanov")
    private String senderName;

    @Schema(description = "destination post office index", example = "234567")
    private int recipientIndex;

    @Schema(description = "destination address description", example = "Red Square, 1")
    private String recipientAddress;

    @Schema(description = "recipient name", example = "Anna Volkova")
    private String recipientName;
}
