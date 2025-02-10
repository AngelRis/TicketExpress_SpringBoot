package com.example.ticketexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusOperatorNotFoundException extends RuntimeException {
    public BusOperatorNotFoundException(Long id) {
        super("Prevoznikot so id: " + id + " ne postoi!");
    }
}

