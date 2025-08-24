package org.papertrail.persistence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.papertrail.persistence.dto.MessageLogContentDTO;
import org.papertrail.persistence.entity.MessageLogContent;
import org.papertrail.persistence.exceptions.MessageAlreadyLoggedException;
import org.papertrail.persistence.exceptions.MessageNotFoundException;
import org.papertrail.persistence.mapper.MessageLogContentMapper;
import org.papertrail.persistence.repository.MessageLogContentRepository;
import org.papertrail.persistence.util.AnsiColor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageLogContentService {

    private final MessageLogContentMapper mapper;
    private final MessageLogContentRepository repository;

    @Transactional
    @CachePut(value = "messageContent", key = "#messageLogContentDTO.messageId")
    public MessageLogContentDTO saveMessage(MessageLogContentDTO messageLogContentDTO){

        log.info("{}Attempting to save message with ID={}{}", AnsiColor.YELLOW, messageLogContentDTO.getMessageId(), AnsiColor.RESET);

        if(repository.existsById(messageLogContentDTO.getMessageId())){
            throw new MessageAlreadyLoggedException("Message has already been logged before. A logged message can only be updated or deleted.");
        }

        MessageLogContent messageLogContent = mapper.toEntity(messageLogContentDTO);
        repository.save(messageLogContent);

        log.info("{}Successfully saved message with ID={}{}", AnsiColor.GREEN, messageLogContentDTO.getMessageId(), AnsiColor.RESET);
        return messageLogContentDTO;
    }

    @Transactional (readOnly = true)
    @Cacheable(value = "messageContent", key = "#messageId")
    public MessageLogContentDTO findMessageById(Long messageId) {

        log.info("{}Cache MISS - Fetching message with ID={}{}", AnsiColor.YELLOW, messageId, AnsiColor.RESET);
        MessageLogContent messageLogContent = repository.findById(messageId)
                .orElseThrow(()-> new MessageNotFoundException("Message with the given ID hasn't been logged before"));

        log.info("{}Found message with ID={}{}", AnsiColor.BLUE, messageId, AnsiColor.RESET);
        return mapper.toDTO(messageLogContent);
    }

    @Transactional
    @CachePut(value = "messageContent", key = "#updatedMessage.messageId")
    public MessageLogContentDTO updateMessage(MessageLogContentDTO updatedMessage) {

        log.info("{}Attempting to update message with ID={}{}", AnsiColor.YELLOW, updatedMessage.getMessageId(), AnsiColor.RESET);
        if(!repository.existsById(updatedMessage.getMessageId())){
            throw new MessageNotFoundException("Message with the given ID hasn't been logged before");
        }

        MessageLogContent updatedMessageEntity = mapper.toEntity(updatedMessage);
        repository.save(updatedMessageEntity);
        log.info("{}Successfully updated message with ID={}{}", AnsiColor.GREEN, updatedMessage.getMessageId(), AnsiColor.RESET);
        return updatedMessage;

    }

    @Transactional
    @CacheEvict(value = "messageContent", key = "#messageId")
    public void deleteMessage(Long messageId) {

        log.info("{}Attempting to delete message with ID={}{}", AnsiColor.YELLOW, messageId, AnsiColor.RESET);
        if(!repository.existsById(messageId)){
            throw new MessageNotFoundException("Message hasn't been logged or the ID is invalid");
        }

        repository.deleteById(messageId);
        log.info("{}Successfully deleted message with ID={}{}", AnsiColor.GREEN, messageId, AnsiColor.RESET);
    }
}
