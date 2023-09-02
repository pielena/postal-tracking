package com.github.pielena.postal.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.decorator.ItemDecorator;
import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.dto.ItemDtoRs;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.enums.PostOfficeType;
import com.github.pielena.postal.tracking.enums.ItemType;
import com.github.pielena.postal.tracking.enums.State;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                .id(UUID.randomUUID())
                .type(ItemType.LETTER)
                .senderName("Ivan")
                .recipientIndex(123456)
                .recipientAddress("Spring Street, 22")
                .recipientName("Anna")
                .operationHistory(List.of(createTestOperationDto()))
                .build();
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
