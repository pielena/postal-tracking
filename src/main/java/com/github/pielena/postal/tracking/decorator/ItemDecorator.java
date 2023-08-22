package com.github.pielena.postal.tracking.decorator;

import com.github.pielena.postal.tracking.converter.ItemConverter;
import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.dto.ItemDtoRs;
import com.github.pielena.postal.tracking.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemDecorator {

    private final ItemConverter itemConverter;
    private final ItemService itemService;

    public Page<ItemDtoRs> getAll(int page, int pageSize) {
        return itemService.getAll(page, pageSize).map(itemConverter::entityToDto);
    }

    public ItemDtoRs createOne(ItemDtoRq itemDtoRq) {
        return itemConverter.entityToDto(itemService.create(itemDtoRq));
    }
}
