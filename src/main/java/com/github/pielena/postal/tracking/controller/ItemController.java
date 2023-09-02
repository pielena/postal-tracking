package com.github.pielena.postal.tracking.controller;

import com.github.pielena.postal.tracking.decorator.ItemDecorator;
import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.dto.ItemDtoRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${application.endpoint.item}")
@RequiredArgsConstructor
@Tag(name = "Postal item Controller", description = "Manage Postal items")
public class ItemController {

    private final ItemDecorator itemDecorator;

    @GetMapping
    @Operation(summary = "Get page with postal itemDtoRs")
    public Page<ItemDtoRs> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        return itemDecorator.getAll(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new postal item")
    public ItemDtoRs create(@RequestBody ItemDtoRq itemDtoRq) {
        return itemDecorator.createOne(itemDtoRq);
    }
}
