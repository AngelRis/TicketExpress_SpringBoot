package com.example.ticketexpress.exception;

import java.time.LocalTime;

public class WrongTermException extends RuntimeException {
    public WrongTermException(LocalTime time) {
        super("Автобусот во "+time+" веќе замина, одберете друг термин.");
    }
}
