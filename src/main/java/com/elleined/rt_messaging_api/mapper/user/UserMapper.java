package com.elleined.rt_messaging_api.mapper.user;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.mapper.chat.GroupChatMapper;
import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.mapper.mention.MentionMapper;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.mapper.reaction.ReactionMapper;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {
                PrivateChatMapper.class,
                GroupChatMapper.class,
                MentionMapper.class,
                MessageMapper.class,
                ReactionMapper.class
        }
)
public interface UserMapper extends CustomMapper<User, UserDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "privateChats", source = "privateChats"),
            @Mapping(target = "groupChats", source = "groupChats"),
            @Mapping(target = "messages", source = "messages"),
            @Mapping(target = "reactions", source = "reactions"),
            @Mapping(target = "receivedMentions", source = "receivedMentions"),
    })
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "privateChats", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "groupChats", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "messages", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "receivedMentions", expression = "java(new java.util.ArrayList<>())"),
    })
    User toEntity(String name,
                  String image);
}