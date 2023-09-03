package com.github.pielena.postal.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.decorator.OperationDecorator;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.enums.PostOfficeType;
import com.github.pielena.postal.tracking.exception.GlobalExceptionHandler;
import com.github.pielena.postal.tracking.exception.S404NotFoundException;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.enums.State;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static com.github.pielena.postal.tracking.exception.ExceptionMessageConstants.NOT_FOUND_BY_ID_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void givenItemId_whenGetByItemId_thenStatus200andListOfOperationDtoReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();
        final List<OperationDto> operationDtoList = List.of(operationDto, operationDto);

        when(operationDecorator.getByItemId(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565b")))
                .thenReturn(operationDtoList);

        mockMvc.perform(get("/api/v1/items/{itemId}/operations", "7af49324-d3a3-4550-9448-38f00103565b")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(operationDtoList)))
                .andExpect(jsonPath("$[0].postOfficeIndex", Matchers.is(123456)))
                .andExpect(jsonPath("$[0].postOfficeType", Matchers.is(PostOfficeType.TRANSIT.name())))
                .andExpect(jsonPath("$[0].id", Matchers.hasLength(36)));
    }

    @Test
    public void givenItemId_whenGetByNotExistingItem_thenStatus404andErrorMessageReturned() throws Exception {
        final UUID id = UUID.fromString("7af49324-d3a3-4550-9448-38f00103565c");

        when(operationDecorator.getByItemId(any()))
                .thenThrow(new S404NotFoundException(String.format(NOT_FOUND_BY_ID_MESSAGE,
                        Item.class.getSimpleName(), id)));

        mockMvc.perform(
                        get("/api/v1/items/{itemId}/operations", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.is("Item with Id: 7af49324-d3a3-4550-9448-38f00103565c not found")));

        verify(operationDecorator, times(1)).getByItemId(id);
    }

    @Test
    public void givenItemIdAndOperationDto_whenCreate_thenStatus201andOperationDtoReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();

        when(operationDecorator.createOne(any(), any())).thenReturn(operationDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/items/{itemId}/operations", "7af49324-d3a3-4550-9448-38f00103565b")
                        .content(objectMapper.writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(content().json(objectMapper.writeValueAsString(operationDto)));
    }

    @Test
    public void givenItemIdAndOperationDto_whenCreateByNotExistingItem_thenStatus404andErrorMessageReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();

        when(operationDecorator.createOne(any(), any()))
                .thenThrow(new S404NotFoundException(String.format(NOT_FOUND_BY_ID_MESSAGE,
                        Item.class.getSimpleName(), UUID.fromString("7af49324-d3a3-4550-9448-38f00103565c"))));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/items/{itemId}/operations", "7af49324-d3a3-4550-9448-38f00103565c")
                        .content(objectMapper.writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.is("Item with Id: 7af49324-d3a3-4550-9448-38f00103565c not found")));

        verify(operationDecorator, times(1)).createOne(any(), any());
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
