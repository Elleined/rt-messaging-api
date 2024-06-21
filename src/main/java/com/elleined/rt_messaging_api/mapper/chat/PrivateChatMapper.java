package com.elleined.rt_messaging_api.mapper.chat;

import com.elleined.rt_messaging_api.dto.chat.PrivateChatDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PrivateChatMapper extends CustomMapper<PrivateChat, PrivateChatDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "messageIds", expression = "java(privateChat.messageIds())"),
            @Mapping(target = "nicknames", expression = "java(privateChat.getNicknameDTOs())"),
            @Mapping(target = "receiverId", source = "receiver.id")
    })
    PrivateChatDTO toDTO(PrivateChat privateChat);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "messages", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "nicknames", expression = "java(new java.util.HashMap<>())"),
            @Mapping(target = "receiver", source = "receiver"),
    })
    PrivateChat toEntity(User creator,
                         User receiver);
}
