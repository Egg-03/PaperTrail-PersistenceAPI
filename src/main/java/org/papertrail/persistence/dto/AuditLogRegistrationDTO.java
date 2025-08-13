package org.papertrail.persistence.dto;

import lombok.Data;

@Data
public class AuditLogRegistrationDTO {

    private long guildId;
    private long channelId;
}
