package com.github.pielena.postal.tracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.pielena.postal.tracking.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Schema(description = "Response Postal item DTO")
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemDtoRs {

    @Schema(description = "postal item id", example = "c8cd3aee-62af-4239-a5da-23965734a733")
    private UUID id;

    @Schema(description = "postal item type", example = "LETTER")
    private ItemType type;

    @Schema(description = "sender name", example = "Ivan Ivanov")
    private String senderName;

    @Schema(description = "destination post office index", example = "234567")
    private int recipientIndex;

    @Schema(description = "destination address description", example = "Red Square, 1")
    private String recipientAddress;

    @Schema(description = "recipient name", example = "Anna Volkova")
    private String recipientName;

    @Schema(description = "last tracking operation")
    private OperationDto lastOperation;

    @Schema(description = "tracking history shows only for single item request")
    private List<OperationDto> operationHistory;
}
