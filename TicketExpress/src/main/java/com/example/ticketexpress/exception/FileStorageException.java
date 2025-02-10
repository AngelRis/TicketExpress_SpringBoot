package com.example.ticketexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException {
    public FileStorageException(String fileName,Throwable cause) {
        super("Greska pri zacuvuvajne na fajlot: " + fileName, cause);
    }
}
