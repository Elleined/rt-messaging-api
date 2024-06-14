package com.elleined.rt_messaging_api.mapper.chat;

import com.elleined.rt_messaging_api.dto.chat.PrivateChatDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PrivateChatMapper extends CustomMapper<PrivateChat, PrivateChatDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
    })
    PrivateChatDTO toDTO(PrivateChat privateChat);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    PrivateChat toEntity();
}
