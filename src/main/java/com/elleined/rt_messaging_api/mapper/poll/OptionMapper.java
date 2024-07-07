package com.elleined.rt_messaging_api.mapper.poll;

import com.elleined.rt_messaging_api.dto.poll.OptionDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.poll.Option;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class,
                PollMapper.class
        }
)
public interface OptionMapper extends CustomMapper<Option, OptionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "option", source = "option"),
            @Mapping(target = "pollDTO", source = "poll")
    })
    OptionDTO toDTO(Option option);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "option", source = "option"),
            @Mapping(target = "poll", source = "poll"),
            @Mapping(target = "votingUsers", expression = "java(new java.util.HashSet<>())")
    })
    Option toEntity(User creator,
                    Poll poll,
                    String option);
}
