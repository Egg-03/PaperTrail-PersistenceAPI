package org.papertrail.persistence.service;

import lombok.RequiredArgsConstructor;
import org.papertrail.persistence.dto.AuditLogRegistrationDTO;
import org.papertrail.persistence.entity.AuditLogRegistration;
import org.papertrail.persistence.exceptions.GuildAlreadyRegisteredException;
import org.papertrail.persistence.exceptions.GuildNotFoundException;
import org.papertrail.persistence.mapper.AuditLogRegistrationMapper;
import org.papertrail.persistence.repository.AuditLogRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogRegistrationService {

    private final AuditLogRegistrationMapper mapper;
    private final AuditLogRegistrationRepository repository;

    @Transactional
    public AuditLogRegistrationDTO registerGuild(AuditLogRegistrationDTO auditLogRegistrationDTO) {

        if (repository.existsById(auditLogRegistrationDTO.getGuildId())){
            throw new GuildAlreadyRegisteredException("guild is already registered for audit logging");
        }

        AuditLogRegistration auditLogRegistration = mapper.toEntity(auditLogRegistrationDTO);
        repository.saveAndFlush(auditLogRegistration);
        return auditLogRegistrationDTO;
    }

    public AuditLogRegistrationDTO findByGuild(Long guildId) {

        AuditLogRegistration auditLogRegistration = repository.findById(guildId)
                .orElseThrow(()->new GuildNotFoundException("guild is not registered for audit logging"));

        return mapper.toDTO(auditLogRegistration);
    }

    @Transactional
    public void unregisterGuild (Long guildId) {

        AuditLogRegistration auditLogRegistration = repository.findById(guildId)
                .orElseThrow(()->new GuildNotFoundException("guild is not registered for audit logging"));

        repository.delete(auditLogRegistration);
    }
}
