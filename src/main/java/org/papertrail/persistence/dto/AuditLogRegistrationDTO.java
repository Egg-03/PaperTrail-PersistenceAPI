package org.papertrail.persistence.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuditLogRegistrationDTO {

    @NotNull(message = "GuildID cannot be null")
    private Long guildId;

    @NotNull(message = "ChannelID cannot be null")
    private Long channelId;
}
