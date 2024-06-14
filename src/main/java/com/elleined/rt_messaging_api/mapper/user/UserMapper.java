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
    })
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    User toEntity(String name);
}
