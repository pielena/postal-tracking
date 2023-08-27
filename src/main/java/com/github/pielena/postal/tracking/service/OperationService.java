package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.persistence.entity.Operation;

import java.util.List;
import java.util.UUID;

public interface OperationService {

    List<Operation> getByItemId(UUID id);

    Operation createOne(UUID itemId, OperationDto operationDto);
}
