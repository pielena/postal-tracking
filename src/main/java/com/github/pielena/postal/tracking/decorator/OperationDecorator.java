package com.github.pielena.postal.tracking.decorator;

import com.github.pielena.postal.tracking.converter.OperationConverter;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.dto.OperationSearchDtoRq;
import com.github.pielena.postal.tracking.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationDecorator {

    private final OperationConverter operationConverter;
    private final OperationService operationService;

    public OperationDto createOne(OperationDto operationDto) {
        return operationConverter.entityToDto(operationService.createOne(operationDto));
    }

    public Page<OperationDto> getAllByRequest(OperationSearchDtoRq request) {
        return operationService.getAllByRequest(request).map(operationConverter::entityToDto);
    }
}
