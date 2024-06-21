package com.elleined.rt_messaging_api.mapper.message;

import com.elleined.rt_messaging_api.dto.message.PinMessageDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.message.PinMessage;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PinMessageMapper extends CustomMapper<PinMessage, PinMessageDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "messageId", source = "message.id"),
            @Mapping(target = "chatId", source = "chat.id")
    })
    PinMessageDTO toDTO(PinMessage pinMessage);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "message", source = "message"),
            @Mapping(target = "chat", source = "chat"),
    })
    PinMessage toEntity(User creator,
                        Message message,
                        Chat chat);
}
