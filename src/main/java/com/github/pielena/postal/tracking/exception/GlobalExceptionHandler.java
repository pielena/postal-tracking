package com.github.pielena.postal.tracking.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ExceptionDto> handleException(RestException ex) {

        return ResponseEntity.status(ex.getHttpStatus())
                .body(ExceptionDto.builder()
                        .message(ex.getMsg())
                        .httpStatus(ex.getHttpStatus())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ExceptionDto exceptionDto = new ExceptionDto(NOT_FOUND, "No handler found");
        exceptionDto.setDebugMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

        return new ResponseEntity<>(exceptionDto, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        ExceptionDto exceptionDto = new ExceptionDto(INTERNAL_SERVER_ERROR, "Something went wrong");
        exceptionDto.setDebugMessage(ex.getMessage());

        return new ResponseEntity<>(exceptionDto, INTERNAL_SERVER_ERROR);
    }
}

