package com.elleined.rt_messaging_api.mapper.poll;

import com.elleined.rt_messaging_api.dto.poll.PollDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PollMapper extends CustomMapper<Poll, PollDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "question", source = "question"),
            @Mapping(target = "groupChatId", source = "groupChat.id"),
            @Mapping(target = "optionIds", expression = "java(poll.optionIds())"),
    })
    PollDTO toDTO(Poll poll);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "question", source = "question"),
            @Mapping(target = "groupChat", source = "groupChat"),
            @Mapping(target = "options", expression = "java(new java.util.ArrayList<>())"),
    })
    Poll toEntity(User creator,
                  GroupChat groupChat,
                  String question);
}
