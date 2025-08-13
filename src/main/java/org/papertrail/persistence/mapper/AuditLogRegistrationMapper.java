package org.papertrail.persistence.mapper;

import org.mapstruct.Mapper;
import org.papertrail.persistence.dto.AuditLogRegistrationDTO;
import org.papertrail.persistence.entity.AuditLogRegistration;

@Mapper (componentModel = "spring")
public interface AuditLogRegistrationMapper {

    AuditLogRegistration toEntity(AuditLogRegistrationDTO auditLogRegistrationDTO);

    AuditLogRegistrationDTO toDTO(AuditLogRegistration auditLogRegistration);
}
