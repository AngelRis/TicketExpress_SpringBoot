package com.example.ticketexpress.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Обиди се со друг username");
    }
}
