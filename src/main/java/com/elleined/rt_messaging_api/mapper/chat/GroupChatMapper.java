package com.elleined.rt_messaging_api.mapper.chat;

import com.elleined.rt_messaging_api.dto.chat.GroupChatDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        uses = UserMapper.class
)
public interface GroupChatMapper extends CustomMapper<GroupChat, GroupChatDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "picture", source = "picture"),
    })
    GroupChatDTO toDTO(GroupChat groupChat);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "messages", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "pinMessages", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "nicknames", expression = "java(new java.util.HashMap<>())"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "picture", source = "picture"),
            @Mapping(target = "receivers", source = "receivers"),
            @Mapping(target = "polls", expression = "java(new java.util.ArrayList<>())")
    })
    GroupChat toEntity(User creator,
                       String name,
                       String picture,
                       Set<User> receivers);
}
