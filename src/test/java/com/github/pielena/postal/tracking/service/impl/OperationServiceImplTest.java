package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.exception.S404NotFoundException;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import com.github.pielena.postal.tracking.persistence.entity.PostOffice;
import com.github.pielena.postal.tracking.enums.State;
import com.github.pielena.postal.tracking.persistence.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private ItemServiceImpl itemService;

    @Mock
    private PostOfficeServiceImpl postOfficeService;

    @Test
    public void givenItemId_whenItemDoesNotExist_thenThrowException() {
        final UUID itemId = UUID.fromString("7af49324-d3a3-4550-9448-38f00103565c");
        OperationDto operationDto = OperationDto.builder()
                .itemId(itemId)
                .build();

        when(itemService.getById(itemId)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(S404NotFoundException.class,
                () -> operationService.createOne(operationDto));
        assertEquals("Item with Id: 7af49324-d3a3-4550-9448-38f00103565c not found", thrown.getMessage());
    }

    @Test
    public void givenOperationDto_whenPostOfficeDoesNotExist_thenThrowException() {
        final UUID itemId = UUID.randomUUID();
        final OperationDto operationDto = OperationDto.builder()
                .itemId(itemId)
                .postOfficeIndex(123456)
                .build();

        when(itemService.getById(itemId)).thenReturn(Optional.of(new Item()));
        when(postOfficeService.findByIndex(operationDto.getPostOfficeIndex())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(S404NotFoundException.class,
                () -> operationService.createOne(operationDto));

        assertEquals("PostOffice with index: 123456 not found", thrown.getMessage());
    }

    @Test
    public void givenOperationDto_whenCreate_thenOperationReturned() {
        final UUID itemId = UUID.randomUUID();
        final PostOffice postOffice = PostOffice.builder()
                .index(123456)
                .build();
        final Item item = Item.builder()
                .id(itemId)
                .destinationPostOffice(postOffice)
                .build();
        final OperationDto operationDto = OperationDto.builder()
                .itemId(itemId)
                .postOfficeIndex(123456)
                .state(State.ARRIVED)
                .build();
        final Operation operation = Operation.builder()
                .id(UUID.randomUUID())
                .item(item)
                .postOffice(postOffice)
                .state(State.ARRIVED)
                .isDestination(true)
                .build();

        when(itemService.getById(itemId)).thenReturn(Optional.of(item));
        when(postOfficeService.findByIndex(operationDto.getPostOfficeIndex())).thenReturn(Optional.of(postOffice));
        when(operationRepository.save(any())).thenReturn(operation);

        Operation result = operationService.createOne(operationDto);

        assertNotNull(result.getId());
        verify(operationRepository, times(1)).save(any());
    }
}