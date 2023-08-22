package com.github.pielena.postal.tracking.dto;

import com.github.pielena.postal.tracking.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemDtoRs {
    private UUID id;
    private ItemType type;
    private String senderName;
    private int recipientIndex;
    private String recipientAddress;
    private String recipientName;
    private List<OperationDto> operationHistory;
}
