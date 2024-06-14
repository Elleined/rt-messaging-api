package com.elleined.rt_messaging_api.mapper.reaction;

import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        imports = {
                Reaction.Emoji.class
        }
)
public interface ReactionMapper extends CustomMapper<Reaction, ReactionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "emoji", source = "emoji"),
            @Mapping(target = "message", source = "message"),
    })
    ReactionDTO toDTO(Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "emoji", source = "emoji"),
            @Mapping(target = "message", source = "message"),
    })
    Reaction toEntity(User creator,
                      Reaction.Emoji emoji,
                      Message message);
}
