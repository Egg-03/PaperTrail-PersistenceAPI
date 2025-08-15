package org.papertrail.persistence.service;

import lombok.RequiredArgsConstructor;
import org.papertrail.persistence.dto.MessageLogContentDTO;
import org.papertrail.persistence.entity.MessageLogContent;
import org.papertrail.persistence.exceptions.MessageAlreadyLoggedException;
import org.papertrail.persistence.exceptions.MessageNotFoundException;
import org.papertrail.persistence.mapper.MessageLogContentMapper;
import org.papertrail.persistence.repository.MessageLogContentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageLogContentService {

    private final MessageLogContentMapper mapper;
    private final MessageLogContentRepository repository;

    @Transactional
    @CachePut(value = "messageContent", key = "#messageLogContentDTO.messageId")
    public MessageLogContentDTO saveMessage(MessageLogContentDTO messageLogContentDTO){

        if(repository.existsById(messageLogContentDTO.getMessageId())){
            throw new MessageAlreadyLoggedException("Message has already been logged before. A logged message can only be updated or deleted.");
        }

        MessageLogContent messageLogContent = mapper.toEntity(messageLogContentDTO);
        repository.save(messageLogContent);
        return messageLogContentDTO;
    }

    @Transactional (readOnly = true)
    @Cacheable(value = "messageContent", key = "#messageId")
    public MessageLogContentDTO findMessageById(Long messageId) {
        MessageLogContent messageLogContent = repository.findById(messageId)
                .orElseThrow(()-> new MessageNotFoundException("Message hasn't been logged"));

        return mapper.toDTO(messageLogContent);
    }

    @Transactional
    @CachePut(value = "messageContent", key = "#updatedMessage.messageId")
    public MessageLogContentDTO updateMessage(MessageLogContentDTO updatedMessage) {

        if(!repository.existsById(updatedMessage.getMessageId())){
            throw new MessageNotFoundException("Message hasn't been logged");
        }

        MessageLogContent updatedMessageEntity = mapper.toEntity(updatedMessage);
        repository.save(updatedMessageEntity);
        return updatedMessage;

    }

    @Transactional
    @CacheEvict(value = "messageContent", key = "#messageId")
    public void deleteMessage(Long messageId) {

        if(!repository.existsById(messageId)){
            throw new MessageNotFoundException("Message hasn't been logged or the ID is invalid");
        }

        repository.deleteById(messageId);
    }
}
