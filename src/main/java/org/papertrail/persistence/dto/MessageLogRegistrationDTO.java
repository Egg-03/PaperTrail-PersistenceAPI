package org.papertrail.persistence.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageLogRegistrationDTO {

    @NotNull(message = "GuildID cannot be null")
    private long guildId;

    @NotNull(message = "ChannelID cannot be null")
    private long channelId;

}
