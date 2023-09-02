package com.github.pielena.postal.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.decorator.ItemDecorator;
import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.dto.ItemDtoRs;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.enums.ItemType;
import com.github.pielena.postal.tracking.enums.PostOfficeType;
import com.github.pielena.postal.tracking.enums.State;
import com.github.pielena.postal.tracking.exception.S404ResourceNotFoundException;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerMockMvcUnitTest {

    @MockBean
    private ItemDecorator itemDecorator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenItemPage_whenGetItems_thenStatus200andItemPageReturned() throws Exception {
        final ItemDtoRs itemDtoRs = createTestItemDtoRs();
        final Page<ItemDtoRs> page = new PageImpl<>(List.of(itemDtoRs));

        when(itemDecorator.getAll(anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/items")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    public void givenItemId_whenGetById_thenStatus200andItemRsReturned() throws Exception {
        final ItemDtoRs itemDtoRs = createTestItemDtoRsWithOperationHistory();

        when(itemDecorator.getById(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565b")))
                .thenReturn(itemDtoRs);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/items/{id}", "7af49324-d3a3-4550-9448-38f00103565b")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemDtoRs)));
    }

    @Test
    public void givenItemId_whenGetByNotExistingItem_thenStatus404andErrorMessageReturned() throws Exception {
        final UUID id = UUID.fromString("5af49324-d3a3-4550-9448-38f00103565c");

        when(itemDecorator.getById(id))
                .thenThrow(new S404ResourceNotFoundException(Item.class, id));

        mockMvc.perform(
                        get("/api/v1/items/{id}", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.is("Item with Id: 5af49324-d3a3-4550-9448-38f00103565c not found")));

        verify(itemDecorator, times(1)).getById(id);
    }

    @Test
    public void givenItemDtoRq_whenCreate_thenStatus201andItemDtoRsReturned() throws Exception {
        final ItemDtoRq itemDtoRq = createTestItemDtoRq();
        final ItemDtoRs itemDtoRs = createTestItemDtoRs();

        when(itemDecorator.createOne(itemDtoRq)).thenReturn(itemDtoRs);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/items")
                        .content(objectMapper.writeValueAsString(itemDtoRq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(content().json(objectMapper.writeValueAsString(itemDtoRs)));
    }

    private ItemDtoRq createTestItemDtoRq() {
        return ItemDtoRq.builder()
                .type(ItemType.LETTER)
                .senderIndex(753614)
                .senderName("Ivan")
                .recipientIndex(123456)
                .recipientAddress("Spring Street, 22")
                .recipientName("Anna")
                .build();
    }

    private ItemDtoRs createTestItemDtoRs() {
        return ItemDtoRs.builder()
                .id(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565b"))
                .type(ItemType.LETTER)
                .senderName("Ivan")
                .recipientIndex(123456)
                .recipientAddress("Spring Street, 22")
                .recipientName("Anna")
                .lastOperation(createTestOperationDto())
                .build();
    }

    private ItemDtoRs createTestItemDtoRsWithOperationHistory() {
        ItemDtoRs itemDtoRs = createTestItemDtoRs();
        itemDtoRs.setOperationHistory(List.of(createTestOperationDto()));

        return itemDtoRs;
    }

    private OperationDto createTestOperationDto() {
        return OperationDto.builder()
                .state(State.REGISTERED)
                .date(LocalDateTime.now())
                .postOfficeIndex(753614)
                .postOfficeType(PostOfficeType.TRANSIT)
                .build();
    }
}
