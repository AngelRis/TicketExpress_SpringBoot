package com.example.ticketexpress.exception;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Лозинките не се совпаѓаат.");
    }
}
