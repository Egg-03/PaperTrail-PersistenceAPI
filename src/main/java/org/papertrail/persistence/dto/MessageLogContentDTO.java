package org.papertrail.persistence.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MessageLogContentDTO {

    @NotNull (message = "MessageID cannot be null")
    private Long messageId;

    @Nullable
    private String messageContent;

    @NotNull (message = "AuthorID cannot be null")
    private Long authorId;
}
