package org.papertrail.persistence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
@Schema(
        name = "MessageLogContent",
        description = "Represents a logged message with its ID, content, and author"
)
public class MessageLogContentDTO {

    @NotNull (message = "MessageID cannot be null")
    @Schema(
            description = "Unique identifier of the message",
            example = "112233445566778899",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long messageId;

    @Nullable
    @Schema(description = "Content of the logged message. Can be null if the message was deleted or redacted.", example = "This is a sample logged message")
    private String messageContent;

    @NotNull (message = "AuthorID cannot be null")
    @Schema(
            description = "Unique identifier of the message author",
            example = "998877665544332211",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long authorId;
}
