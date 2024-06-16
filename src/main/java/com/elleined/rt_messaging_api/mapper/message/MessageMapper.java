package com.elleined.rt_messaging_api.mapper.message;

import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        imports = {
                Message.ContentType.class
        }
)
public interface MessageMapper extends CustomMapper<Message, MessageDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "chatId", source = "chat.id"),
            @Mapping(target = "reactionIds", expression = "java(message.reactionIds())"),
            @Mapping(target = "mentionIds", expression = "java(message.mentionIds())"),
    })
    MessageDTO toDTO(Message message);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "chat", source = "chat"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "mentions", expression = "java(new java.util.ArrayList<>())"),
    })
    Message toEntity(User creator,
                     Chat chat,
                     String content,
                     Message.ContentType contentType);
}
