package com.cbt.cbtapp.exception.lessons;


public class FileStorageException extends Exception {


    public FileStorageException(String message) {
        super(message);
    }


    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
