package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface ItemService {

    Page<Item> getAll(int realPageIndex, int pageSize);

    Optional<Item> getById(UUID id);

    Item create(ItemDtoRq itemDtoRq);
}
