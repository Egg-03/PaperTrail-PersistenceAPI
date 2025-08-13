package org.papertrail.persistence.exceptions;

public class GuildNotFoundException extends RuntimeException{

    public GuildNotFoundException(String message) {
        super(message);
    }
}
