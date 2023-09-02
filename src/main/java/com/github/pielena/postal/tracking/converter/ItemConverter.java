package com.github.pielena.postal.tracking.converter;

import com.github.pielena.postal.tracking.dto.ItemDtoRs;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ItemConverter {

    private final OperationConverter operationConverter;

    public ItemDtoRs entityToDto(Item item) {
        ItemDtoRs itemDtoRs = ItemDtoRs.builder()
                .id(item.getId())
                .type(item.getType())
                .senderName(item.getSender().getName())
                .recipientIndex(item.getDestinationPostOffice().getIndex())
                .recipientAddress(item.getRecipient().getAddress().getDescription())
                .recipientName(item.getRecipient().getName())
                .build();

        OperationDto operationDto = item.getOperationHistory().stream()
                .max(Comparator.comparing(Operation::getDate))
                .map(operationConverter::entityToDto)
                .orElseThrow(NoSuchElementException::new);

        itemDtoRs.setLastOperation(operationDto);

        return itemDtoRs;
    }

    public ItemDtoRs entityToDtoWithOperationHistory(Item item) {
        ItemDtoRs itemDtoRs = entityToDto(item);

        List<OperationDto> operationHistory = item.getOperationHistory().stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed())
                .map(operationConverter::entityToDto)
                .toList();
        itemDtoRs.setOperationHistory(operationHistory);

        return itemDtoRs;
    }
}
