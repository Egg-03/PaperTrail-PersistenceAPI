package org.papertrail.persistence.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.papertrail.persistence.dto.AuditLogRegistrationDTO;
import org.papertrail.persistence.service.AuditLogRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/log/audit")
public class AuditLogRegistrationController {

    private final AuditLogRegistrationService service;

    @PostMapping
    public ResponseEntity<AuditLogRegistrationDTO> createRegistration (@RequestBody @Valid AuditLogRegistrationDTO registrationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerGuild(registrationDTO));
    }

    @GetMapping("/{guildId}")
    public ResponseEntity<AuditLogRegistrationDTO> findRegistration (@PathVariable @Valid Long guildId) {
        return ResponseEntity.ok(service.findByGuild(guildId));
    }

    @PutMapping
    public ResponseEntity<AuditLogRegistrationDTO> updateRegistration (@RequestBody @Valid AuditLogRegistrationDTO updatedDTO) {
        return ResponseEntity.ok(service.updateGuild(updatedDTO));
    }

    @DeleteMapping("/{guildId}")
    public ResponseEntity<Void> deleteRegistration (@PathVariable @Valid Long guildId) {
        service.unregisterGuild(guildId);
        return ResponseEntity.noContent().build();
    }
}
