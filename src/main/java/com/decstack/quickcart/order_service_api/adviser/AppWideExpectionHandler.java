package com.decstack.quickcart.order_service_api.adviser;

import com.decstack.quickcart.order_service_api.exception.EntryNotFoundException;
import com.decstack.quickcart.order_service_api.util.StandardResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppWideExpectionHandler {
    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponseDto> handleEntryNotFoundException(EntryNotFoundException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(
                        200, e.getMessage(),
                        e
                ), HttpStatus.NOT_FOUND
        );
    }
}