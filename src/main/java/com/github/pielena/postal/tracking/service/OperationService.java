package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.dto.OperationSearchDtoRq;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import org.springframework.data.domain.Page;

public interface OperationService {

    Page<Operation> getAllByRequest(OperationSearchDtoRq request);

    Operation createOne(OperationDto operationDto);
}
