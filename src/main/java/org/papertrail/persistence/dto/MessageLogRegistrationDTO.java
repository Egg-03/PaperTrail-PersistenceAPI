package org.papertrail.persistence.dto;

import lombok.Data;

@Data
public class MessageLogRegistrationDTO {

    private long guildId;
    private long channelId;

}
