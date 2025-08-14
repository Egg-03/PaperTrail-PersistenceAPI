package org.papertrail.persistence.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.papertrail.persistence.dto.AuditLogRegistrationDTO;
import org.papertrail.persistence.service.AuditLogRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/log/audit")
public class AuditLogRegistrationController {

    private final AuditLogRegistrationService service;

    @PostMapping("/register")
    public ResponseEntity<AuditLogRegistrationDTO> registerGuild (@RequestBody @Valid AuditLogRegistrationDTO registrationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerGuild(registrationDTO));
    }
}
