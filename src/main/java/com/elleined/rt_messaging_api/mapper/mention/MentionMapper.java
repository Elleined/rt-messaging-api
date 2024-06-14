package com.elleined.rt_messaging_api.mapper.mention;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MentionMapper extends CustomMapper<Mention, MentionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
    })
    MentionDTO toDTO(Mention mention);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
    })
    Mention toEntity();
}
