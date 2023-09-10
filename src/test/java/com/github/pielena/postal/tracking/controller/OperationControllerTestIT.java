package com.github.pielena.postal.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.dto.OperationDto;
import com.github.pielena.postal.tracking.enums.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OperationControllerTestIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/insert.sql")
    public void whenSearchByStateAndDateTo_thenStatus200AndOperationPageReturned() throws Exception {
        mockMvc.perform(get("/api/v1/operations?state=registered&dateto=27-08-2023")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.numberOfElements").value(2));
    }

    @Test
    @Sql("/insert.sql")
    public void givenOperationDto_whenCreate_thenStatus201andOperationDtoReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();

        mockMvc.perform(post("/api/v1/operations")
                        .content(new ObjectMapper().writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.postOfficeIndex").value(567890))
                .andExpect(jsonPath("$.postOfficeType").value("TRANSIT"))
                .andExpect(jsonPath("$.state").value("ARRIVED"));
    }

    @Test
    @Sql("/insert.sql")
    public void givenOperationDto_whenCreateWithNotExistingItem_thenStatus404andErrorMessageReturned() throws Exception {
        final OperationDto operationDto = createTestOperationDto();
        operationDto.setItemId(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565c"));

        mockMvc.perform(post("/api/v1/operations")
                        .content(new ObjectMapper().writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Item with Id: 7af49324-d3a3-4550-9448-38f00103565c not found"));
    }

    private OperationDto createTestOperationDto() {
        return OperationDto.builder()
                .itemId(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565b"))
                .postOfficeIndex(567890)
                .state(State.ARRIVED)
                .build();
    }
}