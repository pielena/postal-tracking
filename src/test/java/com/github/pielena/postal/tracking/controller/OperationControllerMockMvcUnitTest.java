package com.github.pielena.postal.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.decorator.OperationDecorator;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.enums.PostOfficeType;
import com.github.pielena.postal.tracking.enums.State;
import com.github.pielena.postal.tracking.exception.GlobalExceptionHandler;
import com.github.pielena.postal.tracking.exception.S404NotFoundException;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static com.github.pielena.postal.tracking.exception.ExceptionMessageConstants.NOT_FOUND_BY_ID_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({OperationController.class, GlobalExceptionHandler.class})
public class OperationControllerMockMvcUnitTest {

    @MockBean
    private OperationDecorator operationDecorator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenOperationDto_whenCreate_thenStatus201andOperationDtoReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();
        operationDto.setItemId(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565b"));

        when(operationDecorator.createOne(any())).thenReturn(operationDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/operations")
                        .content(objectMapper.writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(content().json(objectMapper.writeValueAsString(operationDto)));
    }

    @Test
    public void givenOperationDto_whenCreateWithNotExistingItem_thenStatus404andErrorMessageReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();
        operationDto.setItemId(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565c"));

        when(operationDecorator.createOne(any()))
                .thenThrow(new S404NotFoundException(String.format(NOT_FOUND_BY_ID_MESSAGE,
                        Item.class.getSimpleName(), UUID.fromString("7af49324-d3a3-4550-9448-38f00103565c"))));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/operations")
                        .content(objectMapper.writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.is("Item with Id: 7af49324-d3a3-4550-9448-38f00103565c not found")));

        verify(operationDecorator, times(1)).createOne(any());
    }

    private OperationDto createTestOperationDto() {
        return OperationDto.builder()
                .id(UUID.randomUUID())
                .postOfficeIndex(123456)
                .postOfficeType(PostOfficeType.TRANSIT)
                .state(State.ARRIVED)
                .build();
    }
}
