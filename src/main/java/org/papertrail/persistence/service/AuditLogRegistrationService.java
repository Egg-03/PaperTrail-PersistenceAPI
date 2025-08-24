package org.papertrail.persistence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papertrail.persistence.dto.AuditLogRegistrationDTO;
import org.papertrail.persistence.entity.AuditLogRegistration;
import org.papertrail.persistence.exceptions.GuildAlreadyRegisteredException;
import org.papertrail.persistence.exceptions.GuildNotFoundException;
import org.papertrail.persistence.mapper.AuditLogRegistrationMapper;
import org.papertrail.persistence.repository.AuditLogRegistrationRepository;
import org.papertrail.persistence.util.AnsiColor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogRegistrationService {

    private final AuditLogRegistrationMapper mapper;
    private final AuditLogRegistrationRepository repository;

    @Transactional
    @CachePut(value = "auditLog", key = "#auditLogRegistrationDTO.guildId")
    public AuditLogRegistrationDTO registerGuild(AuditLogRegistrationDTO auditLogRegistrationDTO) {

        log.info("{}Attempting to register audit log guild with ID={}", AnsiColor.YELLOW, auditLogRegistrationDTO.getGuildId());

        if (repository.existsById(auditLogRegistrationDTO.getGuildId())){
            throw new GuildAlreadyRegisteredException("Guild is already registered for audit logging");
        }

        repository.saveAndFlush(mapper.toEntity(auditLogRegistrationDTO));
        log.info("{}Successfully registered audit log guild with ID={}", AnsiColor.GREEN, auditLogRegistrationDTO.getGuildId());
        return auditLogRegistrationDTO;
    }

    @Transactional (readOnly = true)
    @Cacheable(value = "auditLog", key = "#guildId")
    public AuditLogRegistrationDTO findByGuild(Long guildId) {

        log.info("{}Cache MISS - Fetching audit log guild with ID {}", AnsiColor.YELLOW, guildId);
        AuditLogRegistration auditLogRegistration = repository.findById(guildId)
                .orElseThrow(()->
                        new GuildNotFoundException("Guild is not registered for audit logging")
                );

        log.info("{}Found audit log guild with ID={}", AnsiColor.BLUE, guildId);
        return mapper.toDTO(auditLogRegistration);
    }

    @Transactional
    @CachePut(value = "auditLog", key = "#updatedDTO.guildId")
    public AuditLogRegistrationDTO updateGuild(AuditLogRegistrationDTO updatedDTO) {

        log.info("{}Attempting to update audit log guild with ID={}", AnsiColor.YELLOW, updatedDTO.getGuildId());

        if(!repository.existsById(updatedDTO.getGuildId())){
            throw new GuildNotFoundException("Guild is not registered for audit logging");
        }

        repository.save(mapper.toEntity(updatedDTO));
        log.info("{}Successfully updated audit log guild with ID={}", AnsiColor.GREEN, updatedDTO.getGuildId());
        return updatedDTO;
    }

    @Transactional
    @CacheEvict(value = "auditLog", key = "#guildId")
    public void unregisterGuild (Long guildId) {

        log.info("{}Attempting to unregister audit log guild with ID={}", AnsiColor.YELLOW, guildId);

        AuditLogRegistration auditLogRegistration = repository.findById(guildId)
                .orElseThrow(()->
                    new GuildNotFoundException("Guild is not registered for audit logging")
                );

        repository.delete(auditLogRegistration);
        log.info("{}Successfully unregistered audit log guild with ID={}", AnsiColor.GREEN, guildId);
    }
}
