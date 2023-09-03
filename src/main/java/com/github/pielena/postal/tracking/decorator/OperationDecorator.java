package com.github.pielena.postal.tracking.decorator;

import com.github.pielena.postal.tracking.converter.OperationConverter;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import com.github.pielena.postal.tracking.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OperationDecorator {

    private final OperationConverter operationConverter;
    private final OperationService operationService;

    public OperationDto createOne(UUID itemId, OperationDto operationDto) {
        return operationConverter.entityToDto(operationService.createOne(itemId, operationDto));
    }

    public List<OperationDto> getByItemId(UUID itemId) {
        return operationService.getByItemId(itemId).stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed())
                .map(operationConverter::entityToDto)
                .toList();
    }
}
