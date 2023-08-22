package com.github.pielena.postal.tracking.converter;

import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.entity.Operation;
import org.springframework.stereotype.Component;

@Component
public class OperationConverter {

    public OperationDto entityToDto(Operation operation) {

        return OperationDto.builder()
                .id(operation.getId())
                .itemId(operation.getItem().getId())
                .postOfficeIndex(operation.getPostOffice().getIndex())
                .postOfficeType(operation.isDestination() ? "destination" : "transit")
                .state(operation.getState())
                .date(operation.getDate())
                .build();
    }
}
