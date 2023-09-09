package com.github.pielena.postal.tracking.controller;

import com.github.pielena.postal.tracking.decorator.OperationDecorator;
import com.github.pielena.postal.tracking.dto.OperationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items/{itemId}/operations")
@RequiredArgsConstructor
@Tag(name = "Operation Controller", description = "Manage operations")
public class OperationController {

    private final OperationDecorator operationDecorator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new operation by postal item id")
    public OperationDto create(@PathVariable UUID itemId, @Valid @RequestBody OperationDto operationDto) {
        return operationDecorator.createOne(itemId, operationDto);
    }

    @GetMapping
    @Operation(summary = "Get list of operationDto (tracking history) by postal item id")
    public List<OperationDto> getByItemId(@PathVariable UUID itemId) {
        return operationDecorator.getByItemId(itemId);
    }
}
