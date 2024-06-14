package com.elleined.rt_messaging_api.mapper.chat;

import com.elleined.rt_messaging_api.dto.chat.GroupChatDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GroupChatMapper extends CustomMapper<GroupChat, GroupChatDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
    })
    GroupChatDTO toDTO(GroupChat groupChat);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    GroupChat toEntity();
}
