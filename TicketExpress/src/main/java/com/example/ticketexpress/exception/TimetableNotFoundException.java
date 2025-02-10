package com.example.ticketexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TimetableNotFoundException extends RuntimeException {
    public TimetableNotFoundException(Long id) {
        super("Vozniot red so id: " + id + " ne postoi!");
    }
}
