package org.papertrail.persistence.exceptions;

public class MessageAlreadyLoggedException extends RuntimeException {

    public MessageAlreadyLoggedException(String message) {
        super(message);
    }
}
