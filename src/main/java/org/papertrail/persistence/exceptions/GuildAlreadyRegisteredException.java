package org.papertrail.persistence.exceptions;

public class GuildAlreadyRegisteredException extends RuntimeException{

    public GuildAlreadyRegisteredException(String message) {
        super(message);
    }
}
