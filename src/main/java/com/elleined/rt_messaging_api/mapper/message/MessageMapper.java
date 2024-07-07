package com.elleined.rt_messaging_api.mapper.message;

import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.mapper.chat.GroupChatMapper;
import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        imports = {
                Message.ContentType.class,
                Message.Status.class
        },
        uses = {
                UserMapper.class
        }
)
public interface MessageMapper extends CustomMapper<Message, MessageDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "chatDTO", source = "chat"),
    })
    MessageDTO toDTO(Message message);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "chat", source = "chat"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "mentions", expression = "java(new java.util.ArrayList<>())"),
    })
    Message toEntity(User creator,
                     Chat chat,
                     String content,
                     Message.ContentType contentType);
}
