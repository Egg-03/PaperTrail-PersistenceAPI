package org.papertrail.persistence.service;

import lombok.RequiredArgsConstructor;
import org.papertrail.persistence.dto.MessageLogRegistrationDTO;
import org.papertrail.persistence.entity.MessageLogRegistration;
import org.papertrail.persistence.exceptions.GuildAlreadyRegisteredException;
import org.papertrail.persistence.exceptions.GuildNotFoundException;
import org.papertrail.persistence.mapper.MessageLogRegistrationMapper;
import org.papertrail.persistence.repository.MessageLogRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageLogRegistrationService {

    private final MessageLogRegistrationMapper mapper;
    private final MessageLogRegistrationRepository repository;

    @Transactional
    public MessageLogRegistrationDTO registerGuild(MessageLogRegistrationDTO messageLogRegistrationDTO){

        if(repository.existsById(messageLogRegistrationDTO.getGuildId())){
            throw new GuildAlreadyRegisteredException("guild already registered for message logging");
        }

        MessageLogRegistration messageLogRegistration = mapper.toEntity(messageLogRegistrationDTO);
        repository.saveAndFlush(messageLogRegistration);
        return messageLogRegistrationDTO;
    }

    @Transactional (readOnly = true)
    public MessageLogRegistrationDTO findByGuild(Long guildId){

        MessageLogRegistration messageLogRegistration = repository.findById(guildId)
                .orElseThrow(()-> new GuildNotFoundException("guild is not registered for message logging"));

        return mapper.toDTO(messageLogRegistration);
    }

    @Transactional
    public MessageLogRegistrationDTO updateGuild (MessageLogRegistrationDTO updatedDTO) {

        if (!repository.existsById(updatedDTO.getGuildId())) {
            throw new GuildNotFoundException("guild is not registered for message logging");
        }

        repository.save(mapper.toEntity(updatedDTO));
        return updatedDTO;
    }

    @Transactional
    public void unregisterGuild(Long guildId){

        MessageLogRegistration messageLogRegistration = repository.findById(guildId)
                .orElseThrow(()-> new GuildNotFoundException("guild is not registered for message logging"));

        repository.delete(messageLogRegistration);
    }
}
