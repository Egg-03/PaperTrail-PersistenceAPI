package org.papertrail.persistence.dto;

import lombok.Data;

@Data
public class MessageLogContentDTO {

    private long messageId;
    private String messageContent;
    private long authorId;
}
