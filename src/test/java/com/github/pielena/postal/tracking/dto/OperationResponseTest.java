package com.github.pielena.postal.tracking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.postal.tracking.enums.PostOfficeType;
import com.github.pielena.postal.tracking.enums.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JsonTest
class OperationResponseTest {

    @Autowired
    private JacksonTester<OperationDto> jacksonTester;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSerializeObject() throws IOException {
        assertNotNull(objectMapper);

        final OperationDto operationDto = OperationDto.builder()
                .id(UUID.randomUUID())
                .postOfficeIndex(123456)
                .postOfficeType(PostOfficeType.TRANSIT)
                .state(State.ARRIVED)
                .date(LocalDateTime.parse("2023-09-20T19:00:00.123"))
                .build();

        final JsonContent<OperationDto> result = jacksonTester.write(operationDto);

        assertThat(result).hasJsonPathStringValue("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.postOfficeIndex").isEqualTo(123456);
        assertThat(result).extractingJsonPathStringValue("$.state").isEqualTo(State.ARRIVED.name());
        assertThat(result).extractingJsonPathStringValue("$.date").isEqualTo("20-09-2023 19:00:00");
    }
}