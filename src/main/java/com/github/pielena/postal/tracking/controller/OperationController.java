package com.github.pielena.postal.tracking.controller;

import com.github.pielena.postal.tracking.decorator.OperationDecorator;
import com.github.pielena.postal.tracking.dto.OperationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items/{itemId}/operations")
public class OperationController {

    private final OperationDecorator operationDecorator;

    @PostMapping
    public OperationDto create(@PathVariable UUID itemId, @RequestBody OperationDto operationDto) {
        return operationDecorator.createOne(itemId, operationDto);
    }

    @GetMapping
    public List<OperationDto> getByItemId(@PathVariable UUID itemId) {
        return operationDecorator.getByItemId(itemId);
    }
}
