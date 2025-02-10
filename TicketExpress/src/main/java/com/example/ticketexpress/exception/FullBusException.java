package com.example.ticketexpress.exception;

public class FullBusException extends RuntimeException {
    public FullBusException(String message) {
        super(message);
    }
}
