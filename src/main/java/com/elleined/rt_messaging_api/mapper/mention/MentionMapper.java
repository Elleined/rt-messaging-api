package com.elleined.rt_messaging_api.mapper.mention;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class,
                MessageMapper.class
        }
)
public interface MentionMapper extends CustomMapper<Mention, MentionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "mentionedUser", source = "mentionedUser"),
            @Mapping(target = "message", source = "message"),
    })
    MentionDTO toDTO(Mention mention);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "mentionedUser", source = "mentionedUser"),
            @Mapping(target = "message", source = "message"),
    })
    Mention toEntity(User creator,
                     User mentionedUser,
                     Message message);
}
