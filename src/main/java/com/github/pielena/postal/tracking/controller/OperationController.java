package com.github.pielena.postal.tracking.controller;

import com.github.pielena.postal.tracking.decorator.OperationDecorator;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.dto.OperationSearchDtoRq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
@Tag(name = "Operation Controller", description = "Manage operations")
public class OperationController {

    private final OperationDecorator operationDecorator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new operation")
    public OperationDto create(@Valid @RequestBody OperationDto operationDto) {
        return operationDecorator.createOne(operationDto);
    }

    @GetMapping
    @Operation(summary = "Operation searching by different parameters (all of them are optional)")
    public Page<OperationDto> getAllByRequest(OperationSearchDtoRq request) {
        return operationDecorator.getAllByRequest(request);
    }
}
