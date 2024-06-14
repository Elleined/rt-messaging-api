package com.elleined.rt_messaging_api.mapper.reaction;

import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReactionMapper extends CustomMapper<Reaction, ReactionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
    })
    ReactionDTO toDTO(Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    Reaction toEntity();
}
