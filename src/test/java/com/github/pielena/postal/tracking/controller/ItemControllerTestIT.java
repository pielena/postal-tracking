package com.github.pielena.postal.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.persistence.entity.ItemType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTestIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/insert.sql")
    void whenGetAll_thenStatus200AndItemPageReturned() throws Exception {

        mockMvc.perform(get("/api/v1/items")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].id").value("c8cd3aee-62af-4239-a5da-23965734a733"))
                .andExpect(jsonPath("$.content[1].id").value("7af49324-d3a3-4550-9448-38f00103565b"));
    }

    @Test
    @Sql("/insert.sql")
    void givenItemDtoRq_whenCreateItem_thenStatus201andItemDtoRsReturned() throws Exception {
        final ItemDtoRq itemDtoRq = createTestItemDtoRq();

        mockMvc.perform(post("/api/v1/items")
                        .content(new ObjectMapper().writeValueAsString(itemDtoRq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("LETTER"))
                .andExpect(jsonPath("$.senderName").value("Sender"))
                .andExpect(jsonPath("$.recipientIndex").value("123456"))
                .andExpect(jsonPath("$.recipientAddress").value("Spring Street, 22"))
                .andExpect(jsonPath("$.recipientName").value("Elena"))
                .andExpect(jsonPath("$.operationHistory").exists())
                .andExpect(jsonPath("$.operationHistory").isArray())
                .andExpect(jsonPath("$.operationHistory[0].state").value("REGISTERED"))
                .andExpect(jsonPath("$.operationHistory[0].postOfficeType").value("TRANSIT"))
                .andExpect(jsonPath("$.operationHistory[0].postOfficeIndex").value(753614));
    }

    private ItemDtoRq createTestItemDtoRq() {
        return ItemDtoRq.builder()
                .type(ItemType.LETTER)
                .senderIndex(753614)
                .senderName("Sender")
                .recipientIndex(123456)
                .recipientName("Elena")
                .recipientAddress("Spring Street, 22")
                .build();
    }
}