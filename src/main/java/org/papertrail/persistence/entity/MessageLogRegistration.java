package org.papertrail.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "message_log_registration_table", schema = "public")
public class MessageLogRegistration {

    @Id
    @Column(name = "guild_id")
    private long guildId;

    @Column (name = "channel_id", unique = true, nullable = false)
    private long channelId;
}
