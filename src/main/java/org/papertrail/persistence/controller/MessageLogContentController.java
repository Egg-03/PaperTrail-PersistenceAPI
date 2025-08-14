package org.papertrail.persistence.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.papertrail.persistence.dto.MessageLogContentDTO;
import org.papertrail.persistence.service.MessageLogContentService;
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
@RequestMapping("/api/v1/content/message")
public class MessageLogContentController {

    private final MessageLogContentService service;

    @PostMapping
    public ResponseEntity<MessageLogContentDTO> saveMessage(@RequestBody @Valid MessageLogContentDTO message){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveMessage(message));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageLogContentDTO> readMessage (@PathVariable @Valid Long messageId) {
        return ResponseEntity.ok(service.findMessageById(messageId));
    }

    @PutMapping
    public ResponseEntity<MessageLogContentDTO> updateMessage (@RequestBody @Valid MessageLogContentDTO updatedMessage) {
        return ResponseEntity.ok(service.updateMessage(updatedMessage));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable @Valid Long messageId) {
        service.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }


}
