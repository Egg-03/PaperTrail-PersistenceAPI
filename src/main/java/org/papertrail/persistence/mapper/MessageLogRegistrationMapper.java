package org.papertrail.persistence.mapper;

import org.mapstruct.Mapper;
import org.papertrail.persistence.dto.MessageLogRegistrationDTO;
import org.papertrail.persistence.entity.MessageLogRegistration;

@Mapper (componentModel = "spring")
public interface MessageLogRegistrationMapper {

    MessageLogRegistration toEntity (MessageLogRegistrationDTO messageLogRegistrationDTO);

    MessageLogRegistrationDTO toDTO (MessageLogRegistration messageLogRegistration);
}
