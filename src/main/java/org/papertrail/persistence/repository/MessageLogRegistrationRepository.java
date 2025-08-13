package org.papertrail.persistence.repository;

import org.papertrail.persistence.entity.MessageLogRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRegistrationRepository extends JpaRepository<MessageLogRegistration, Long> {

    boolean existsByChannelId (Long channelId);
}
