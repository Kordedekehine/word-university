package com.cbt.cbtapp.exception.lessons;

/**
 * Exception thrown when storing a file fails.
 */
public class FileStorageException extends Exception {

    /**
     * Constructor.
     *
     * @param message is the message describing the exception.
     */
    public FileStorageException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message is the message describing the exception.
     * @param cause   is the exception that caused this exception to be thrown.
     */
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
