package com.github.pielena.postal.tracking.dto;

import com.github.pielena.postal.tracking.persistence.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemDtoRq {

    private UUID id;
    private ItemType type;
    private int senderIndex;
    private String senderName;
    private int recipientIndex;
    private String recipientAddress;
    private String recipientName;
}
