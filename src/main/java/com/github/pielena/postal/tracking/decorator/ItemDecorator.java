package com.github.pielena.postal.tracking.decorator;

import com.github.pielena.postal.tracking.converter.ItemConverter;
import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.dto.ItemDtoRs;
import com.github.pielena.postal.tracking.exception.S404NotFoundException;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.github.pielena.postal.tracking.exception.ExceptionMessageConstants.NOT_FOUND_BY_ID_MESSAGE;

@Component
@RequiredArgsConstructor
public class ItemDecorator {

    private final ItemConverter itemConverter;
    private final ItemService itemService;

    public Page<ItemDtoRs> getAll(int page, int pageSize) {
        return itemService.getAll(page, pageSize).map(itemConverter::entityToDto);
    }

    public ItemDtoRs getById(UUID id) {
        return itemService.getById(id)
                .map(itemConverter::entityToDtoWithOperationHistory)
                .orElseThrow(() -> new S404NotFoundException(String.format(NOT_FOUND_BY_ID_MESSAGE,
                        Item.class.getSimpleName(), id)));
    }

    public ItemDtoRs createOne(ItemDtoRq itemDtoRq) {
        return itemConverter.entityToDto(itemService.create(itemDtoRq));
    }
}
