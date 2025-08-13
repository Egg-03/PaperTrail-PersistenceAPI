package org.papertrail.persistence.repository;

import org.papertrail.persistence.entity.AuditLogRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRegistrationRepository extends JpaRepository<AuditLogRegistration, Long> {

}
