package org.papertrail.persistence.mapper;

import org.mapstruct.Mapper;
import org.papertrail.persistence.dto.MessageLogContentDTO;
import org.papertrail.persistence.entity.MessageLogContent;

@Mapper (componentModel = "spring")
public interface MessageLogContentMapper {

    MessageLogContent toEntity (MessageLogContentDTO messageLogContentDTO);

    MessageLogContentDTO toDTO (MessageLogContent messageLogContent);
}
