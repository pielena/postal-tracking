package com.github.pielena.postal.tracking.converter;

import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.dto.PostOfficeType;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import org.springframework.stereotype.Component;

@Component
public class OperationConverter {

    public OperationDto entityToDto(Operation operation) {

        return OperationDto.builder()
                .id(operation.getId())
                .postOfficeIndex(operation.getPostOffice().getIndex())
                .postOfficeType(operation.isDestination() ? PostOfficeType.DESTINATION : PostOfficeType.TRANSIT)
                .state(operation.getState())
                .date(operation.getDate())
                .build();
    }
}
