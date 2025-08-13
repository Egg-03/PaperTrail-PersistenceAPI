package org.papertrail.persistence.repository;

import org.papertrail.persistence.entity.MessageLogContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogContentRepository extends JpaRepository<MessageLogContent, Long> {

}
