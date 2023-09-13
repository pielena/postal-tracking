package com.github.pielena.postal.tracking.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExceptionDto {

    private String code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    public ExceptionDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionDto(String code, String message, List<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}
