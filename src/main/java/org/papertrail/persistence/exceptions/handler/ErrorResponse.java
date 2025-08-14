package org.papertrail.persistence.exceptions.handler;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timeStamp) {
}
