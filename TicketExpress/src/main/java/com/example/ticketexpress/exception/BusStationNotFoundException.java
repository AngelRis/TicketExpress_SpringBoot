package com.example.ticketexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusStationNotFoundException extends RuntimeException {
    public BusStationNotFoundException(Long id) {
        super("Avtobuskata stanica so id: " + id + " ne postoi!");
    }
}
