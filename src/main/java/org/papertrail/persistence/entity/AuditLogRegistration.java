package org.papertrail.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "audit_log_table", schema = "public")
public class AuditLogRegistration {

    @Id
    @Column (name = "guild_id")
    private long guildId;

    @Column (name = "channel_id", unique = true, nullable = false)
    private long channelId;
}
