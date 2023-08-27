package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import com.github.pielena.postal.tracking.persistence.entity.PostOffice;
import com.github.pielena.postal.tracking.exception.S404ResourceNotFoundException;
import com.github.pielena.postal.tracking.persistence.repository.OperationRepository;
import com.github.pielena.postal.tracking.service.ItemService;
import com.github.pielena.postal.tracking.service.OperationService;
import com.github.pielena.postal.tracking.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final ItemService itemService;
    private final PostOfficeService postOfficeService;

    @Override
    public List<Operation> getByItemId(UUID itemId) {
        Item item = itemService.getById(itemId)
                .orElseThrow(() -> new S404ResourceNotFoundException(Item.class, itemId));

        return operationRepository.getAllByItem(item);
    }

    @Override
    @Transactional
    public Operation createOne(UUID itemId, OperationDto operationDto) {

        Item item = itemService.getById(itemId)
                .orElseThrow(() -> new S404ResourceNotFoundException(Item.class, itemId));
        PostOffice postOffice = postOfficeService.findByIndex(operationDto.getPostOfficeIndex())
                .orElseThrow(() -> new S404ResourceNotFoundException(PostOffice.class, operationDto.getPostOfficeIndex()));

        Operation operation = Operation.builder()
                .item(item)
                .postOffice(postOffice)
                .state(operationDto.getState())
                .date(LocalDateTime.now())
                .isDestination(postOffice.equals(item.getDestinationPostOffice()))
                .build();

        return save(operation);
    }

    private Operation save(Operation operation) {
        return operationRepository.save(operation);
    }
}
